package com.lashmanager.clients.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.lashmanager.clients.AbstractIntegrationTest;
import com.lashmanager.clients.application.command.CreateClientCommand;
import com.lashmanager.clients.application.command.DeactivateClientCommand;
import com.lashmanager.clients.application.command.DeleteClientCommand;
import com.lashmanager.clients.application.command.ReactivateClientCommand;
import com.lashmanager.clients.application.command.UpdateClientCommand;
import com.lashmanager.clients.application.service.ClientApplicationService;
import com.lashmanager.clients.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.ClientQueryService;
import com.lashmanager.clients.domain.port.out.ClientAppointmentPort;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("Clientes — integração")
class ClientITest extends AbstractIntegrationTest {

    @Autowired
    ClientApplicationService clientApplicationService;

    @Autowired
    ClientQueryService clientQueryService;

    @MockBean
    ClientAppointmentPort clientAppointmentPort;

    @BeforeEach
    void setUpAppointmentPortStub() {
        given(clientAppointmentPort.findFutureActiveByClientId(any(), any())).willReturn(Collections.emptyList());
    }

    private Client createClient(String name, String phone) {
        return clientApplicationService.when(new CreateClientCommand(name, phone, null, null, null));
    }

    @Test
    @DisplayName("deve criar, buscar, atualizar e excluir um cliente com sucesso")
    void createFindUpdateDelete_fullCrudFlow() {
        String phone = uniquePhone();

        Client created = createClient("Ana Lima", phone);
        UUID id = created.getId();

        assertThat(created.getName()).isEqualTo("Ana Lima");
        assertThat(created.isActive()).isTrue();

        Client found = clientQueryService.getById(id);
        assertThat(found.getName()).isEqualTo("Ana Lima");
        assertThat(found.getPhone()).isEqualTo(phone);

        clientApplicationService.when(
                new UpdateClientCommand(id, "Ana Costa", phone, null, null, null));

        Client updated = clientQueryService.getById(id);
        assertThat(updated.getName()).isEqualTo("Ana Costa");

        clientApplicationService.when(new DeleteClientCommand(id));

        assertThatThrownBy(() -> clientQueryService.getById(id)).isInstanceOf(ClientNotFoundException.class);
    }

    @Test
    @DisplayName("deve lançar ClientAlreadyExistsException ao cadastrar telefone duplicado")
    void create_withDuplicatePhone_throwsClientAlreadyExistsException() {
        String phone = uniquePhone();
        createClient("Ana Lima", phone);

        assertThatThrownBy(() -> createClient("Beatriz Costa", phone)).isInstanceOf(ClientAlreadyExistsException.class);
    }

    @Test
    @DisplayName("deve persistir active=false no banco após desativar o cliente")
    void deactivate_persistsActiveFalseInDatabase() {
        String phone = uniquePhone();
        Client created = createClient("Ana Lima", phone);
        UUID id = created.getId();

        clientApplicationService.when(new DeactivateClientCommand(id, false));

        Client result = clientQueryService.getById(id);
        assertThat(result.isActive()).isFalse();
    }

    @Test
    @DisplayName("deve persistir active=true no banco após reativar o cliente")
    void reactivate_persistsActiveTrueInDatabase() {
        String phone = uniquePhone();
        Client created = createClient("Ana Lima", phone);
        UUID id = created.getId();

        clientApplicationService.when(new DeactivateClientCommand(id, false));
        clientApplicationService.when(new ReactivateClientCommand(id));

        Client result = clientQueryService.getById(id);
        assertThat(result.isActive()).isTrue();
    }

    @Test
    @DisplayName("deve retornar apenas clientes que correspondem ao texto de busca")
    void listBySearch_returnsOnlyMatchingClients() {
        String phoneAna = uniquePhone();
        String phoneBea = uniquePhone();
        createClient("Ana Lima", phoneAna);
        createClient("Beatriz Costa", phoneBea);

        Page<Client> page = clientQueryService.list("Ana Lima", null, PageRequest.of(0, 10));

        assertThat(page.getContent())
                .isNotEmpty()
                .allSatisfy(c -> assertThat(c.getName()).containsIgnoringCase("Ana Lima"));
    }

    @Test
    @DisplayName("deve retornar apenas clientes inativos ao filtrar por active=false")
    void listByActiveFilter_returnsOnlyInactiveClients() {
        String phoneAtivo = uniquePhone();
        String phoneInativo = uniquePhone();

        Client ativo = createClient("Cliente Ativo", phoneAtivo);
        Client inativo = createClient("Cliente Inativo", phoneInativo);
        clientApplicationService.when(new DeactivateClientCommand(inativo.getId(), false));

        Page<Client> page = clientQueryService.list("", false, PageRequest.of(0, 50));

        assertThat(page.getContent())
                .extracting(Client::getId)
                .contains(inativo.getId())
                .doesNotContain(ativo.getId());
    }

    private String uniquePhone() {
        return "119" + (System.nanoTime() % 100_000_000L);
    }
}
