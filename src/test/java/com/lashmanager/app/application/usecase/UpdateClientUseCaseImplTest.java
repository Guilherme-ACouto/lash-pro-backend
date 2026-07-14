package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.port.in.CreateClientUseCase;
import com.lashmanager.app.domain.port.in.UpdateClientUseCase;
import com.lashmanager.app.domain.port.out.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UpdateClientUseCaseImplTest {

    @Mock
    private ClientRepository clientRepository;

    private UpdateClientUseCaseImpl useCase;
    private UUID clientId;
    private Client existingClient;

    @BeforeEach
    void setUp() {
        useCase = new UpdateClientUseCaseImpl(clientRepository);
        clientId = UUID.randomUUID();
        existingClient = Client.builder()
                .id(clientId).name("Ana Lima").phone("11999999999")
                .active(true).createdAt(LocalDateTime.now()).build();
    }

    @Test
    void execute_withValidData_returnsUpdatedResult() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));
        given(clientRepository.existsByPhoneAndIdNot(any(), any())).willReturn(false);
        given(clientRepository.save(any())).willAnswer(inv -> inv.getArgument(0));

        UpdateClientUseCase.UpdateClientCommand command = new UpdateClientUseCase.UpdateClientCommand(
                "Ana Costa", "11999999999", null, null, null
        );

        CreateClientUseCase.ClientResult result = useCase.execute(clientId, command);

        assertThat(result.name()).isEqualTo("Ana Costa");
    }

    @Test
    void execute_withUnknownId_throwsClientNotFoundException() {
        given(clientRepository.findById(any())).willReturn(Optional.empty());

        UpdateClientUseCase.UpdateClientCommand command = new UpdateClientUseCase.UpdateClientCommand(
                "Ana Costa", "11999999999", null, null, null
        );

        assertThatThrownBy(() -> useCase.execute(clientId, command))
                .isInstanceOf(ClientNotFoundException.class);

        then(clientRepository).should(never()).save(any());
    }

    @Test
    void execute_withPhoneOwnedByAnotherClient_throwsClientAlreadyExistsException() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));
        given(clientRepository.existsByPhoneAndIdNot(any(), any())).willReturn(true);

        UpdateClientUseCase.UpdateClientCommand command = new UpdateClientUseCase.UpdateClientCommand(
                "Ana Costa", "11988888888", null, null, null
        );

        assertThatThrownBy(() -> useCase.execute(clientId, command))
                .isInstanceOf(ClientAlreadyExistsException.class);

        then(clientRepository).should(never()).save(any());
    }

    @Test
    void execute_withSamePhoneOfSameClient_succeeds() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));
        given(clientRepository.existsByPhoneAndIdNot(any(), any())).willReturn(false);
        given(clientRepository.save(any())).willAnswer(inv -> inv.getArgument(0));

        UpdateClientUseCase.UpdateClientCommand command = new UpdateClientUseCase.UpdateClientCommand(
                "Ana Lima", "11999999999", null, null, null
        );

        useCase.execute(clientId, command);

        then(clientRepository).should(times(1)).save(any());
    }
}
