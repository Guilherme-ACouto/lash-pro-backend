package com.lashmanager.clients.application.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.out.ClientQueryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientQueryService")
class ClientQueryServiceImplTest {

    @Mock
    private ClientQueryRepository clientQueryRepository;

    private ClientQueryServiceImpl queryService;

    @BeforeEach
    void setUp() {
        queryService = new ClientQueryServiceImpl(clientQueryRepository);
    }

    @Nested
    @DisplayName("Buscar cliente por id")
    class GetById {

        @Test
        @DisplayName("deve retornar o cliente quando o id existe")
        void getById_withExistingId_returnsClient() {
            Client client = Client.builder()
                    .id(UUID.randomUUID())
                    .name("Ana Lima")
                    .phone("11999999999")
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .build();

            given(clientQueryRepository.findById(client.getId())).willReturn(Optional.of(client));

            Client result = queryService.getById(client.getId());

            assertThat(result.getId()).isEqualTo(client.getId());
            assertThat(result.getName()).isEqualTo("Ana Lima");
        }

        @Test
        @DisplayName("deve lançar ClientNotFoundException quando o id não existe")
        void getById_withUnknownId_throwsClientNotFoundException() {
            UUID unknownId = UUID.randomUUID();

            given(clientQueryRepository.findById(unknownId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> queryService.getById(unknownId)).isInstanceOf(ClientNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Listar clientes")
    class List_ {

        @Test
        @DisplayName("deve passar string vazia ao repositório quando a busca é nula")
        void list_withNullSearch_passesEmptyStringToRepository() {
            ArgumentCaptor<String> searchCaptor = ArgumentCaptor.forClass(String.class);
            given(clientQueryRepository.findAll(any(), any(), any())).willReturn(Page.empty());

            queryService.list(null, null, PageRequest.of(0, 10));

            verify(clientQueryRepository).findAll(searchCaptor.capture(), isNull(), any());
            assertThat(searchCaptor.getValue()).isEqualTo("");
        }

        @Test
        @DisplayName("deve remover espaços em branco da busca antes de repassar ao repositório")
        void list_withWhitespaceSearch_trimsBeforePassing() {
            ArgumentCaptor<String> searchCaptor = ArgumentCaptor.forClass(String.class);
            given(clientQueryRepository.findAll(any(), any(), any())).willReturn(Page.empty());

            queryService.list("  Ana  ", null, PageRequest.of(0, 10));

            verify(clientQueryRepository).findAll(searchCaptor.capture(), isNull(), any());
            assertThat(searchCaptor.getValue()).isEqualTo("Ana");
        }

        @Test
        @DisplayName("deve delegar ao repositório e devolver a página de clientes")
        void list_withValidParams_delegatesToRepository() {
            Client client = Client.builder()
                    .id(UUID.randomUUID())
                    .name("Ana Lima")
                    .phone("11999999999")
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .build();

            given(clientQueryRepository.findAll(any(), any(), any())).willReturn(new PageImpl<>(List.of(client)));

            Page<Client> result = queryService.list("Ana", null, PageRequest.of(0, 10));

            assertThat(result.getTotalElements()).isEqualTo(1);
            assertThat(result.getContent().get(0).getName()).isEqualTo("Ana Lima");
        }
    }
}
