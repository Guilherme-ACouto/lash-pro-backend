package com.lashmanager.app.application.usecase;

import com.lashmanager.app.AbstractIntegrationTest;
import com.lashmanager.app.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.port.in.CreateClientUseCase;
import com.lashmanager.app.domain.port.in.DeactivateClientUseCase;
import com.lashmanager.app.domain.port.in.DeleteClientUseCase;
import com.lashmanager.app.domain.port.in.GetClientUseCase;
import com.lashmanager.app.domain.port.in.ListClientsUseCase;
import com.lashmanager.app.domain.port.in.UpdateClientUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClientITest extends AbstractIntegrationTest {

    @Autowired CreateClientUseCase createClientUseCase;
    @Autowired UpdateClientUseCase updateClientUseCase;
    @Autowired GetClientUseCase getClientUseCase;
    @Autowired ListClientsUseCase listClientsUseCase;
    @Autowired DeleteClientUseCase deleteClientUseCase;
    @Autowired DeactivateClientUseCase deactivateClientUseCase;

    private CreateClientUseCase.ClientResult createClient(String name, String phone) {
        return createClientUseCase.execute(
                new CreateClientUseCase.CreateClientCommand(name, phone, null, null, null));
    }

    @Test
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
    void create_withDuplicatePhone_throwsClientAlreadyExistsException() {
        String phone = uniquePhone();
        createClient("Ana Lima", phone);

        assertThatThrownBy(() -> createClient("Beatriz Costa", phone))
                .isInstanceOf(ClientAlreadyExistsException.class);
    }

    @Test
    void deactivate_persistsActiveFalseInDatabase() {
        String phone = uniquePhone();
        CreateClientUseCase.ClientResult created = createClient("Ana Lima", phone);
        UUID id = created.id();

        deactivateClientUseCase.deactivate(id, false);

        CreateClientUseCase.ClientResult result = getClientUseCase.execute(id);
        assertThat(result.active()).isFalse();
    }

    @Test
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
