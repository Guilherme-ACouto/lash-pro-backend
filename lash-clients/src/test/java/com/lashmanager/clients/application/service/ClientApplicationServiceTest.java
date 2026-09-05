package com.lashmanager.clients.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.then;

import com.lashmanager.clients.application.command.CreateClientCommand;
import com.lashmanager.clients.application.command.DeactivateClientCommand;
import com.lashmanager.clients.application.command.DeleteClientCommand;
import com.lashmanager.clients.application.command.ReactivateClientCommand;
import com.lashmanager.clients.application.command.UpdateClientCommand;
import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.ClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientApplicationService")
class ClientApplicationServiceTest {

    @Mock
    private ClientUseCase clientUseCase;

    @Mock
    private ClientRepository clientRepository;

    private ClientApplicationService service;

    private UUID clientId;
    private Client existingClient;

    @BeforeEach
    void setUp() {
        service = new ClientApplicationService(clientUseCase, clientRepository);
        clientId = UUID.randomUUID();
        existingClient = Client.builder()
                .id(clientId)
                .name("Ana Lima")
                .phone("11999999999")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Nested
    @DisplayName("Criar cliente")
    class Create {

        @Test
        @DisplayName("deve delegar direto pra UseCase (sem buscar entidade prévia)")
        void when_create_delegatesToUseCase() {
            CreateClientCommand command = new CreateClientCommand("Ana Lima", "11999999999", null, null, null);
            given(clientUseCase.create(command)).willReturn(existingClient);

            Client result = service.when(command);

            assertThat(result).isEqualTo(existingClient);
            then(clientRepository).should(never()).findById(any());
        }
    }

    @Nested
    @DisplayName("Atualizar cliente")
    class Update {

        @Test
        @DisplayName("deve buscar o cliente e delegar pra UseCase quando o id existe")
        void when_withExistingId_delegatesToUseCase() {
            given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));

            UpdateClientCommand command =
                    new UpdateClientCommand(clientId, "Ana Costa", "11999999999", null, null, null);

            service.when(command);

            then(clientUseCase).should().update(existingClient, command);
        }

        @Test
        @DisplayName("deve lançar ClientNotFoundException quando o id não existe")
        void when_withUnknownId_throwsClientNotFoundException() {
            given(clientRepository.findById(clientId)).willReturn(Optional.empty());

            UpdateClientCommand command =
                    new UpdateClientCommand(clientId, "Ana Costa", "11999999999", null, null, null);

            assertThatThrownBy(() -> service.when(command)).isInstanceOf(ClientNotFoundException.class);

            then(clientUseCase).should(never()).update(any(), any());
        }
    }

    @Nested
    @DisplayName("Excluir cliente")
    class Delete {

        @Test
        @DisplayName("deve buscar o cliente e delegar pra UseCase quando o id existe")
        void when_withExistingId_delegatesToUseCase() {
            given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));

            service.when(new DeleteClientCommand(clientId));

            then(clientUseCase).should().delete(existingClient);
        }

        @Test
        @DisplayName("deve lançar ClientNotFoundException quando o id não existe")
        void when_withUnknownId_throwsClientNotFoundException() {
            given(clientRepository.findById(clientId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> service.when(new DeleteClientCommand(clientId)))
                    .isInstanceOf(ClientNotFoundException.class);

            then(clientUseCase).should(never()).delete(any());
        }
    }

    @Nested
    @DisplayName("Desativar e reativar cliente")
    class DeactivateReactivate {

        @Test
        @DisplayName("deve buscar o cliente e delegar deactivate pra UseCase quando o id existe")
        void deactivate_withExistingId_delegatesToUseCase() {
            given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));

            service.when(new DeactivateClientCommand(clientId, true));

            then(clientUseCase).should().deactivate(existingClient, true);
        }

        @Test
        @DisplayName("deve lançar ClientNotFoundException ao desativar quando o id não existe")
        void deactivate_withUnknownId_throwsClientNotFoundException() {
            given(clientRepository.findById(clientId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> service.when(new DeactivateClientCommand(clientId, false)))
                    .isInstanceOf(ClientNotFoundException.class);

            then(clientUseCase).should(never()).deactivate(any(), any(Boolean.class));
        }

        @Test
        @DisplayName("deve buscar o cliente e delegar reactivate pra UseCase quando o id existe")
        void reactivate_withExistingId_delegatesToUseCase() {
            given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));

            service.when(new ReactivateClientCommand(clientId));

            then(clientUseCase).should().reactivate(existingClient);
        }

        @Test
        @DisplayName("deve lançar ClientNotFoundException ao reativar quando o id não existe")
        void reactivate_withUnknownId_throwsClientNotFoundException() {
            given(clientRepository.findById(clientId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> service.when(new ReactivateClientCommand(clientId)))
                    .isInstanceOf(ClientNotFoundException.class);

            then(clientUseCase).should(never()).reactivate(any());
        }
    }
}
