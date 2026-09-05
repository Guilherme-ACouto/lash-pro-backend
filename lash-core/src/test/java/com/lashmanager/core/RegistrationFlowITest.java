package com.lashmanager.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lashmanager.core.application.command.ActivateAccountCommand;
import com.lashmanager.core.application.command.RegisterCommand;
import com.lashmanager.core.application.service.ActivateAccountApplicationService;
import com.lashmanager.core.application.service.RegisterApplicationService;
import com.lashmanager.core.domain.exception.ActivationKeyInvalidException;
import com.lashmanager.core.domain.model.User;
import com.lashmanager.core.domain.port.in.ActivateAccountUseCase;
import com.lashmanager.core.domain.port.in.LoginUseCase;
import com.lashmanager.core.domain.port.out.SchemaProvisionerPort;
import com.lashmanager.core.domain.port.out.TenantRepository;
import com.lashmanager.core.domain.port.out.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * Cobre o fluxo completo registro -> ativação -> login. SchemaProvisionerPort é mockado (não roda
 * CREATE SCHEMA/Liquibase de verdade) — mesma escolha da Pontta: testar a orquestração (Tenant
 * criado, usuário ativado, port chamado com o tenantId certo) sem criar um schema Postgres real por
 * execução, o que evitaria acumular schemas no banco de teste a cada rodada.
 */
@DisplayName("Fluxo de registro e ativação — integração")
class RegistrationFlowITest extends AbstractIntegrationTest {

    @Autowired
    private RegisterApplicationService registerApplicationService;

    @Autowired
    private ActivateAccountApplicationService activateAccountApplicationService;

    @Autowired
    private LoginUseCase loginUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @MockBean
    private SchemaProvisionerPort schemaProvisionerPort;

    @Test
    @DisplayName("deve registrar, ativar (chamando o provisionamento) e permitir login no tenant recém-criado")
    void registerActivateAndLogin_fullFlow() {
        String email = "nova" + UUID.randomUUID() + "@teste.com";

        registerApplicationService.when(new RegisterCommand("Nova Usuária", email, "senha123"));

        User pending = userRepository.findByEmail(email).orElseThrow();
        assertThat(pending.isActive()).isFalse();
        assertThat(pending.getActivationKey()).isNotBlank();
        assertThat(pending.getTenantId()).isNotNull();

        ActivateAccountUseCase.ActivationResult activationResult =
                activateAccountApplicationService.when(new ActivateAccountCommand(pending.getActivationKey()));

        assertThat(activationResult.email()).isEqualTo(email);
        assertThat(activationResult.tenantId()).isEqualTo(pending.getTenantId());
        assertThat(tenantRepository.findById(activationResult.tenantId())).isPresent();
        Mockito.verify(schemaProvisionerPort).provision(activationResult.tenantId());

        User activated = userRepository.findByEmail(email).orElseThrow();
        assertThat(activated.isActive()).isTrue();
        assertThat(activated.getActivationKey()).isNull();

        LoginUseCase.LoginResponse loginResponse =
                loginUseCase.execute(new LoginUseCase.LoginCommand(email, "senha123"));

        assertThat(loginResponse.email()).isEqualTo(email);
        assertThat(loginResponse.accessToken()).isNotBlank();
    }

    @Test
    @DisplayName("deve rejeitar ativação com chave inexistente")
    void activate_withInvalidKey_throwsActivationKeyInvalidException() {
        assertThatThrownBy(() ->
                        activateAccountApplicationService.when(new ActivateAccountCommand("chave-que-nao-existe")))
                .isInstanceOf(ActivationKeyInvalidException.class);
    }
}
