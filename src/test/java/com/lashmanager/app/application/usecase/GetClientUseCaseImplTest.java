package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.port.in.CreateClientUseCase.ClientResult;
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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetClientUseCaseImplTest {

    @Mock
    private ClientRepository clientRepository;

    private GetClientUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetClientUseCaseImpl(clientRepository);
    }

    @Test
    void execute_withExistingId_returnsClientResult() {
        Client client = Client.builder()
                .id(UUID.randomUUID())
                .name("Ana Lima")
                .phone("11999999999")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        given(clientRepository.findById(client.getId())).willReturn(Optional.of(client));

        ClientResult result = useCase.execute(client.getId());

        assertThat(result.id()).isEqualTo(client.getId());
        assertThat(result.name()).isEqualTo("Ana Lima");
    }

    @Test
    void execute_withUnknownId_throwsClientNotFoundException() {
        UUID unknownId = UUID.randomUUID();

        given(clientRepository.findById(unknownId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(unknownId))
                .isInstanceOf(ClientNotFoundException.class);
    }
}
