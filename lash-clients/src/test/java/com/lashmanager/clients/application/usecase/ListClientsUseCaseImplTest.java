package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Listar clientes")
class ListClientsUseCaseImplTest {

    @Mock
    private ClientRepository clientRepository;

    private ListClientsUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListClientsUseCaseImpl(clientRepository);
    }

    @Test
    @DisplayName("deve passar string vazia ao repositório quando a busca é nula")
    void execute_withNullSearch_passesEmptyStringToRepository() {
        ArgumentCaptor<String> searchCaptor = ArgumentCaptor.forClass(String.class);
        given(clientRepository.findAll(any(), any(), any())).willReturn(Page.empty());

        useCase.execute(null, null, PageRequest.of(0, 10));

        verify(clientRepository).findAll(searchCaptor.capture(), isNull(), any());
        assertThat(searchCaptor.getValue()).isEqualTo("");
    }

    @Test
    @DisplayName("deve remover espaços em branco da busca antes de repassar ao repositório")
    void execute_withWhitespaceSearch_trimsBeforePassing() {
        ArgumentCaptor<String> searchCaptor = ArgumentCaptor.forClass(String.class);
        given(clientRepository.findAll(any(), any(), any())).willReturn(Page.empty());

        useCase.execute("  Ana  ", null, PageRequest.of(0, 10));

        verify(clientRepository).findAll(searchCaptor.capture(), isNull(), any());
        assertThat(searchCaptor.getValue()).isEqualTo("Ana");
    }

    @Test
    @DisplayName("deve delegar ao repositório e mapear o resultado corretamente")
    void execute_withValidParams_delegatesAndMapsResult() {
        Client client = Client.builder()
                .id(UUID.randomUUID())
                .name("Ana Lima")
                .phone("11999999999")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        given(clientRepository.findAll(any(), any(), any()))
                .willReturn(new PageImpl<>(List.of(client)));

        Page<CreateClientUseCase.ClientResult> result =
                useCase.execute("Ana", null, PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).name()).isEqualTo("Ana Lima");
    }
}
