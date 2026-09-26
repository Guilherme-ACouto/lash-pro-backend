package com.bravapro.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.bravapro.core.application.command.RegisterCommand;
import com.bravapro.core.application.service.RegisterApplicationService;
import com.bravapro.core.domain.exception.EmailAlreadyInUseException;
import com.bravapro.core.domain.model.CommandAuditLog;
import com.bravapro.core.domain.model.Tenant;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.out.CommandAuditLogRepository;
import com.bravapro.core.domain.port.out.TenantRepository;
import com.bravapro.core.domain.port.out.UserRepository;
import com.bravapro.core.infrastructure.multitenancy.TenantSchemaNaming;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("CommandInterceptor — validação e auditoria")
class CommandInterceptorITest extends AbstractIntegrationTest {

    @Autowired
    private RegisterApplicationService registerApplicationService;

    @Autowired
    private CommandAuditLogRepository commandAuditLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TenantSchemaNaming tenantSchemaNaming;

    @Test
    @DisplayName("command inválido lança ConstraintViolationException e não chega a executar o UseCase")
    void invalidCommand_throwsConstraintViolationException_andNeverExecutes() {
        String invalidEmail = "email-sem-arroba";
        RegisterCommand invalid = new RegisterCommand("", invalidEmail, "123");

        assertThatThrownBy(() -> registerApplicationService.when(invalid))
                .isInstanceOf(ConstraintViolationException.class);

        assertThat(userRepository.existsByEmail(invalidEmail)).isFalse();
    }

    @Test
    @DisplayName("command válido com sucesso grava auditoria de sucesso com o payload correto")
    void validCommand_success_recordsSuccessAuditLog() {
        String email = "audit-sucesso-" + UUID.randomUUID() + "@teste.com";

        registerApplicationService.when(new RegisterCommand("Usuária Auditoria", email, "senha123"));

        CommandAuditLog latest = commandAuditLogRepository
                .findLatestByCommandClass("RegisterCommand")
                .orElseThrow();

        assertThat(latest.isSuccess()).isTrue();
        assertThat(latest.getPayloadJson()).contains(email);
    }

    @Test
    @DisplayName("command válido cuja execução lança exceção de negócio grava auditoria de falha")
    void validCommand_businessFailure_recordsFailureAuditLog() {
        String email = "audit-falha-" + UUID.randomUUID() + "@teste.com";
        LocalDateTime now = LocalDateTime.now();
        UUID tenantId = UUID.randomUUID();

        tenantRepository.save(Tenant.builder()
                .id(tenantId)
                .name("Tenant Já Cadastrada")
                .schemaName(tenantSchemaNaming.schemaNameFor(tenantId))
                .active(true)
                .createdAt(now)
                .build());

        userRepository.save(User.builder()
                .id(UUID.randomUUID())
                .name("Já Cadastrada")
                .email(email)
                .password(passwordEncoder.encode("outrasenha"))
                .admin(true)
                .active(true)
                .tenantId(tenantId)
                .createdAt(now)
                .updatedAt(now)
                .build());

        assertThatThrownBy(() ->
                        registerApplicationService.when(new RegisterCommand("Tentativa Duplicada", email, "senha123")))
                .isInstanceOf(EmailAlreadyInUseException.class);

        CommandAuditLog latest = commandAuditLogRepository
                .findLatestByCommandClass("RegisterCommand")
                .orElseThrow();

        assertThat(latest.isSuccess()).isFalse();
    }
}
