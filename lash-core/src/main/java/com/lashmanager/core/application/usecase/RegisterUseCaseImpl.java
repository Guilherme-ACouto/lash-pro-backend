package com.lashmanager.core.application.usecase;

import com.lashmanager.core.domain.exception.EmailAlreadyInUseException;
import com.lashmanager.core.domain.model.Tenant;
import com.lashmanager.core.domain.model.User;
import com.lashmanager.core.domain.model.UserRole;
import com.lashmanager.core.domain.port.in.RegisterUseCase;
import com.lashmanager.core.domain.port.in.ResendActivationUseCase;
import com.lashmanager.core.domain.port.out.EmailPort;
import com.lashmanager.core.domain.port.out.TenantRepository;
import com.lashmanager.core.domain.port.out.UserRepository;
import com.lashmanager.core.infrastructure.multitenancy.TenantSchemaNaming;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterUseCaseImpl implements RegisterUseCase {

  private final UserRepository userRepository;
  private final TenantRepository tenantRepository;
  private final TenantSchemaNaming tenantSchemaNaming;
  private final PasswordEncoder passwordEncoder;
  private final EmailPort emailPort;
  private final ResendActivationUseCase resendActivationUseCase;

  @Value("${app.activation.key-expiration-hours:48}")
  private long activationKeyExpirationHours;

  @Override
  @Transactional
  public RegisterResult execute(RegisterData data) {
    Optional<User> existing = userRepository.findByEmail(data.email());

    if (existing.isPresent()) {
      User existingUser = existing.get();
      if (existingUser.isActive()) {
        throw new EmailAlreadyInUseException(data.email());
      }
      resendActivationUseCase.execute(data.email());
      return new RegisterResult(existingUser.getId(), existingUser.getEmail());
    }

    String activationKey = UUID.randomUUID().toString();
    LocalDateTime now = LocalDateTime.now();

    // tenantId já é atribuído aqui (não na ativação): se o provisionamento do
    // schema falhar e a ativação precisar ser tentada de novo, o retry usa o
    // mesmo tenantId (persistido desde o registro, fora da transação de
    // ativação) — evita gerar um schema órfão a cada nova tentativa. A linha
    // em `tenants` também precisa existir já aqui (inativa) — users.tenant_id
    // tem FK para tenants.id, então não dá pra só atribuir o id sem o registro
    // correspondente; ActivateAccountUseCaseImpl só marca active=true depois.
    UUID tenantId = UUID.randomUUID();
    tenantRepository.save(
        Tenant.builder()
            .id(tenantId)
            .name(data.name())
            .schemaName(tenantSchemaNaming.schemaNameFor(tenantId))
            .active(false)
            .createdAt(now)
            .build());

    User user =
        User.builder()
            .id(UUID.randomUUID())
            .name(data.name())
            .email(data.email())
            .password(passwordEncoder.encode(data.password()))
            .role(UserRole.OWNER)
            .active(false)
            .tenantId(tenantId)
            .activationKey(activationKey)
            .activationKeyExpiry(now.plusHours(activationKeyExpirationHours))
            .createdAt(now)
            .updatedAt(now)
            .build();

    User saved = userRepository.save(user);
    emailPort.sendActivationEmail(saved.getEmail(), saved.getName(), activationKey);
    log.info("Cadastro criado, aguardando ativação: {}", saved.getEmail());

    return new RegisterResult(saved.getId(), saved.getEmail());
  }
}
