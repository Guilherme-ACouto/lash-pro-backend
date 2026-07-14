package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.AbstractIntegrationTest;
import com.lashmanager.clients.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import com.lashmanager.clients.domain.port.in.DeactivateClientUseCase;
import com.lashmanager.clients.domain.port.in.DeleteClientUseCase;
import com.lashmanager.clients.domain.port.in.GetClientUseCase;
import com.lashmanager.clients.domain.port.in.ListClientsUseCase;
import com.lashmanager.clients.domain.port.in.UpdateClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientAppointmentPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Clientes — integração")
class ClientITest extends AbstractIntegrationTest {

    @Autowired CreateClientUseCase createClientUseCase;
    @Autowired UpdateClientUseCase updateClientUseCase;
    @Autowired GetClientUseCase getClientUseCase;
    @Autowired ListClientsUseCase listClientsUseCase;
    @Autowired DeleteClientUseCase deleteClientUseCase;
    @Autowired DeactivateClientUseCase deactivateClientUseCase;

    @MockBean
    ClientAppointmentPort clientAppointmentPort;

    @BeforeEach
    void setUpAppointmentPortStub() {
        given(clientAppointmentPort.findFutureActiveByClientId(any(), any()))
                .willReturn(Collections.emptyList());
    }

    private CreateClientUseCase.ClientResult createClient(String name, String phone) {
        return createClientUseCase.execute(
                new CreateClientUseCase.CreateClientCommand(name, phone, null, null, null));
    }

    @Test
    @DisplayName("deve criar, buscar, atualizar e excluir um cliente com sucesso")
    void createFindUpdateDelete_fullCrudFlow() {
        String phone = uniquePhone();

        CreateClientUseCase.ClientResult created = createClient("Ana Lima", phone);
        UUID id = created.id();

        assertThat(created.name()).isEqualTo("Ana Lima");
        assertThat(created.active()).isTrue();

        CreateClientUseCase.ClientResult found = getClientUseCase.execute(id);
        assertThat(found.name()).isEqualTo("Ana Lima");
        assertThat(found.phone()).isEqualTo(phone);

        updateClientUseCase.execute(id,
                new UpdateClientUseCase.UpdateClientCommand("Ana Costa", phone, null, null, null));

        CreateClientUseCase.ClientResult updated = getClientUseCase.execute(id);
        assertThat(updated.name()).isEqualTo("Ana Costa");

        deleteClientUseCase.execute(id);

        assertThatThrownBy(() -> getClientUseCase.execute(id))
                .isInstanceOf(ClientNotFoundException.class);
    }

    @Test
    @DisplayName("deve lançar ClientAlreadyExistsException ao cadastrar telefone duplicado")
    void create_withDuplicatePhone_throwsClientAlreadyExistsException() {
        String phone = uniquePhone();
        createClient("Ana Lima", phone);

        assertThatThrownBy(() -> createClient("Beatriz Costa", phone))
                .isInstanceOf(ClientAlreadyExistsException.class);
    }

    @Test
    @DisplayName("deve persistir active=false no banco após desativar o cliente")
    void deactivate_persistsActiveFalseInDatabase() {
        String phone = uniquePhone();
        CreateClientUseCase.ClientResult created = createClient("Ana Lima", phone);
        UUID id = created.id();

        deactivateClientUseCase.deactivate(id, false);

        CreateClientUseCase.ClientResult result = getClientUseCase.execute(id);
        assertThat(result.active()).isFalse();
    }

    @Test
    @DisplayName("deve persistir active=true no banco após reativar o cliente")
    void reactivate_persistsActiveTrueInDatabase() {
        String phone = uniquePhone();
        CreateClientUseCase.ClientResult created = createClient("Ana Lima", phone);
        UUID id = created.id();

        deactivateClientUseCase.deactivate(id, false);
        deactivateClientUseCase.reactivate(id);

        CreateClientUseCase.ClientResult result = getClientUseCase.execute(id);
        assertThat(result.active()).isTrue();
    }

    @Test
    @DisplayName("deve retornar apenas clientes que correspondem ao texto de busca")
    void listBySearch_returnsOnlyMatchingClients() {
        String phoneAna = uniquePhone();
        String phoneBea = uniquePhone();
        createClient("Ana Lima", phoneAna);
        createClient("Beatriz Costa", phoneBea);

        Page<CreateClientUseCase.ClientResult> page =
                listClientsUseCase.execute("Ana Lima", null, PageRequest.of(0, 10));

        assertThat(page.getContent())
                .isNotEmpty()
                .allSatisfy(c -> assertThat(c.name()).containsIgnoringCase("Ana Lima"));
    }

    @Test
    @DisplayName("deve retornar apenas clientes inativos ao filtrar por active=false")
    void listByActiveFilter_returnsOnlyInactiveClients() {
        String phoneAtivo = uniquePhone();
        String phoneInativo = uniquePhone();

        CreateClientUseCase.ClientResult ativo = createClient("Cliente Ativo", phoneAtivo);
        CreateClientUseCase.ClientResult inativo = createClient("Cliente Inativo", phoneInativo);
        deactivateClientUseCase.deactivate(inativo.id(), false);

        Page<CreateClientUseCase.ClientResult> page =
                listClientsUseCase.execute("", false, PageRequest.of(0, 50));

        assertThat(page.getContent())
                .extracting(CreateClientUseCase.ClientResult::id)
                .contains(inativo.id())
                .doesNotContain(ativo.id());
    }

    private String uniquePhone() {
        return "119" + (System.nanoTime() % 100_000_000L);
    }
}
