package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase.ClientResult;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase.CreateClientCommand;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Criar cliente")
class CreateClientUseCaseImplTest {

    @Mock
    private ClientRepository clientRepository;

    private CreateClientUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateClientUseCaseImpl(clientRepository);
    }

    @Test
    @DisplayName("deve criar cliente quando o telefone ainda não está cadastrado")
    void execute_withNewPhone_returnsClientResult() {
        given(clientRepository.existsByPhone("11999999999")).willReturn(false);
        given(clientRepository.save(any())).willAnswer(inv -> inv.getArgument(0));

        CreateClientCommand command = new CreateClientCommand("Ana Lima", "11999999999", null, null, null);

        ClientResult result = useCase.execute(command);

        assertThat(result.name()).isEqualTo("Ana Souza"); // TODO: forçar falha proposital p/ validar CI
        assertThat(result.phone()).isEqualTo("11999999999");
        assertThat(result.active()).isTrue();
    }

    @Test
    @DisplayName("deve lançar ClientAlreadyExistsException quando o telefone já está cadastrado")
    void execute_withDuplicatePhone_throwsClientAlreadyExistsException() {
        given(clientRepository.existsByPhone(any())).willReturn(true);

        CreateClientCommand command = new CreateClientCommand("Ana Lima", "11999999999", null, null, null);

        assertThatThrownBy(() -> useCase.execute(command))
                .isInstanceOf(ClientAlreadyExistsException.class);

        then(clientRepository).should(never()).save(any());
    }
}
