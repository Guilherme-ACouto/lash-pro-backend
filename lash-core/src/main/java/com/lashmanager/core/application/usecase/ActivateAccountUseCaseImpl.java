package com.lashmanager.core.application.usecase;

import com.lashmanager.core.domain.exception.ActivationKeyExpiredException;
import com.lashmanager.core.domain.exception.ActivationKeyInvalidException;
import com.lashmanager.core.domain.model.Tenant;
import com.lashmanager.core.domain.model.User;
import com.lashmanager.core.domain.port.in.ActivateAccountUseCase;
import com.lashmanager.core.domain.port.out.SchemaProvisionerPort;
import com.lashmanager.core.domain.port.out.TenantRepository;
import com.lashmanager.core.domain.port.out.UserRepository;
import com.lashmanager.core.infrastructure.multitenancy.TenantSchemaNaming;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ativa a conta: cria o Tenant e ativa o usuário (transação Spring/JPA), depois provisiona o schema
 * físico (conexão própria, fora dessa transação — ver SchemaProvisionerImpl). Se o provisionamento
 * falhar, a exceção propaga e derruba a transação @Transactional inteira — Tenant e ativação do
 * usuário são revertidos (rollback). O schema físico (se chegou a ser criado) não é revertido, mas
 * isso é seguro: um novo retry com a mesma activationKey usa o mesmo tenantId (persistido desde o
 * registro) e o provisionamento é idempotente (CREATE SCHEMA IF NOT EXISTS + changesets já
 * aplicados no Liquibase não rodam de novo).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ActivateAccountUseCaseImpl implements ActivateAccountUseCase {

  private final UserRepository userRepository;
  private final TenantRepository tenantRepository;
  private final SchemaProvisionerPort schemaProvisionerPort;
  private final TenantSchemaNaming tenantSchemaNaming;

  @Override
  @Transactional
  public ActivationResult execute(String activationKey) {
    User user =
        userRepository
            .findByActivationKey(activationKey)
            .orElseThrow(ActivationKeyInvalidException::new);

    if (user.getActivationKeyExpiry() == null
        || user.getActivationKeyExpiry().isBefore(LocalDateTime.now())) {
      throw new ActivationKeyExpiredException();
    }

    UUID tenantId = user.getTenantId();
    activateTenant(tenantId, user.getName());
    activateUser(user);

    schemaProvisionerPort.provision(tenantId);

    if (log.isInfoEnabled()) {
      log.info("Conta ativada: {} (tenant {})", user.getEmail(), tenantId);
    }
    return new ActivationResult(user.getEmail(), tenantId);
  }

  /**
   * A linha em `tenants` já existe (criada, inativa, no registro — ver RegisterUseCaseImpl,
   * necessário por causa da FK de users.tenant_id). Aqui só marca active=true; o fallback de criar
   * do zero cobre um estado inconsistente que não deveria acontecer no fluxo normal.
   */
  private void activateTenant(UUID tenantId, String tenantName) {
    Tenant tenant =
        tenantRepository
            .findById(tenantId)
            .map(
                t ->
                    Tenant.builder()
                        .id(t.getId())
                        .name(t.getName())
                        .schemaName(t.getSchemaName())
                        .active(true)
                        .createdAt(t.getCreatedAt())
                        .build())
            .orElseGet(
                () ->
                    Tenant.builder()
                        .id(tenantId)
                        .name(tenantName)
                        .schemaName(tenantSchemaNaming.schemaNameFor(tenantId))
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .build());
    tenantRepository.save(tenant);
  }

  private void activateUser(User user) {
    userRepository.save(
        User.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .role(user.getRole())
            .active(true)
            .passwordResetToken(user.getPasswordResetToken())
            .passwordResetTokenExpiry(user.getPasswordResetTokenExpiry())
            .tenantId(user.getTenantId())
            .activationKey(null)
            .activationKeyExpiry(null)
            .createdAt(user.getCreatedAt())
            .updatedAt(LocalDateTime.now())
            .build());
  }
}
