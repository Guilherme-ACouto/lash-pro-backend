package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase.ClientResult;
import com.lashmanager.clients.domain.port.out.ClientQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("Buscar cliente por id")
class GetClientUseCaseImplTest {

    @Mock
    private ClientQueryRepository clientQueryRepository;

    private GetClientUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetClientUseCaseImpl(clientQueryRepository);
    }

    @Test
    @DisplayName("deve retornar o cliente quando o id existe")
    void execute_withExistingId_returnsClientResult() {
        Client client = Client.builder()
                .id(UUID.randomUUID())
                .name("Ana Lima")
                .phone("11999999999")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        given(clientQueryRepository.findById(client.getId())).willReturn(Optional.of(client));

        ClientResult result = useCase.execute(client.getId());

        assertThat(result.id()).isEqualTo(client.getId());
        assertThat(result.name()).isEqualTo("Ana Lima");
    }

    @Test
    @DisplayName("deve lançar ClientNotFoundException quando o id não existe")
    void execute_withUnknownId_throwsClientNotFoundException() {
        UUID unknownId = UUID.randomUUID();

        given(clientQueryRepository.findById(unknownId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(unknownId))
                .isInstanceOf(ClientNotFoundException.class);
    }
}
