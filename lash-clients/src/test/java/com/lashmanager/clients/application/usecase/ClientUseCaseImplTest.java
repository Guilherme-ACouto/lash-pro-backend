package com.lashmanager.clients.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

import com.lashmanager.clients.application.command.CreateClientCommand;
import com.lashmanager.clients.application.command.UpdateClientCommand;
import com.lashmanager.clients.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.clients.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.clients.domain.model.AppointmentSummary;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.out.ClientAppointmentPort;
import com.lashmanager.clients.domain.port.out.ClientRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientUseCase")
class ClientUseCaseImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientAppointmentPort clientAppointmentPort;

    private ClientUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new ClientUseCaseImpl(clientRepository, clientAppointmentPort);
    }

    @Nested
    @DisplayName("Criar cliente")
    class Create {

        @Test
        @DisplayName("deve criar cliente quando o telefone ainda não está cadastrado")
        void create_withNewPhone_returnsClient() {
            given(clientRepository.existsByPhone("11999999999")).willReturn(false);
            given(clientRepository.save(any())).willAnswer(inv -> inv.getArgument(0));

            CreateClientCommand command = new CreateClientCommand("Ana Lima", "11999999999", null, null, null);

            Client result = useCase.create(command);

            assertThat(result.getName()).isEqualTo("Ana Lima");
            assertThat(result.getPhone()).isEqualTo("11999999999");
            assertThat(result.isActive()).isTrue();
        }

        @Test
        @DisplayName("deve lançar ClientAlreadyExistsException quando o telefone já está cadastrado")
        void create_withDuplicatePhone_throwsClientAlreadyExistsException() {
            given(clientRepository.existsByPhone(any())).willReturn(true);

            CreateClientCommand command = new CreateClientCommand("Ana Lima", "11999999999", null, null, null);

            assertThatThrownBy(() -> useCase.create(command)).isInstanceOf(ClientAlreadyExistsException.class);

            then(clientRepository).should(never()).save(any());
        }
    }

    @Nested
    @DisplayName("Atualizar cliente")
    class Update {

        private UUID clientId;
        private Client existingClient;

        @BeforeEach
        void setUp() {
            clientId = UUID.randomUUID();
            existingClient = Client.builder()
                    .id(clientId)
                    .name("Ana Lima")
                    .phone("11999999999")
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        @Test
        @DisplayName("deve atualizar os dados do cliente em memória e salvar quando os dados são válidos")
        void update_withValidData_mutatesAndSavesClient() {
            given(clientRepository.existsByPhoneAndIdNot(any(), any())).willReturn(false);
            given(clientRepository.save(any())).willAnswer(inv -> inv.getArgument(0));

            UpdateClientCommand command = new UpdateClientCommand(
                    clientId, "Ana Costa", "11999999999", null, null, null);

            useCase.update(existingClient, command);

            assertThat(existingClient.getName()).isEqualTo("Ana Costa");
            then(clientRepository).should().save(existingClient);
        }

        @Test
        @DisplayName("deve lançar ClientAlreadyExistsException quando o telefone pertence a outro cliente")
        void update_withPhoneOwnedByAnotherClient_throwsClientAlreadyExistsException() {
            given(clientRepository.existsByPhoneAndIdNot(any(), any())).willReturn(true);

            UpdateClientCommand command = new UpdateClientCommand(
                    clientId, "Ana Costa", "11988888888", null, null, null);

            assertThatThrownBy(() -> useCase.update(existingClient, command))
                    .isInstanceOf(ClientAlreadyExistsException.class);

            then(clientRepository).should(never()).save(any());
        }

        @Test
        @DisplayName("deve salvar normalmente quando o telefone informado é o próprio do cliente")
        void update_withSamePhoneOfSameClient_succeeds() {
            given(clientRepository.existsByPhoneAndIdNot(any(), any())).willReturn(false);
            given(clientRepository.save(any())).willAnswer(inv -> inv.getArgument(0));

            UpdateClientCommand command = new UpdateClientCommand(
                    clientId, "Ana Lima", "11999999999", null, null, null);

            useCase.update(existingClient, command);

            then(clientRepository).should(times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("Excluir cliente")
    class Delete {

        private UUID clientId;
        private Client existingClient;

        @BeforeEach
        void setUp() {
            clientId = UUID.randomUUID();
            existingClient = Client.builder()
                    .id(clientId)
                    .name("Ana Lima")
                    .phone("11999999999")
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        @Test
        @DisplayName(
                "deve desvincular agendamentos passados e excluir o cliente na ordem correta quando não há agendamentos futuros")
        void delete_withNoFutureAppointments_deletesInCorrectOrder() {
            given(clientAppointmentPort.findFutureActiveByClientId(eq(clientId), any()))
                    .willReturn(Collections.emptyList());

            useCase.delete(existingClient);

            InOrder inOrder = inOrder(clientAppointmentPort, clientRepository);
            inOrder.verify(clientAppointmentPort).unlinkClientFromPastAppointments(eq(clientId), any());
            inOrder.verify(clientRepository).deleteById(clientId);
        }

        @Test
        @DisplayName("deve lançar HasFutureAppointmentsException quando o cliente possui agendamentos futuros ativos")
        void delete_withFutureActiveAppointments_throwsHasFutureAppointmentsException() {
            AppointmentSummary summary1 = new AppointmentSummary(
                    UUID.randomUUID().toString(),
                    LocalDate.now().plusDays(3),
                    LocalTime.of(10, 0),
                    "Extensão de Cílios");
            AppointmentSummary summary2 = new AppointmentSummary(
                    UUID.randomUUID().toString(), LocalDate.now().plusDays(7), LocalTime.of(14, 30), "Manutenção");

            given(clientAppointmentPort.findFutureActiveByClientId(eq(clientId), any()))
                    .willReturn(List.of(summary1, summary2));

            assertThatThrownBy(() -> useCase.delete(existingClient))
                    .isInstanceOf(HasFutureAppointmentsException.class);

            then(clientRepository).should(never()).deleteById(any());
        }
    }

    @Nested
    @DisplayName("Desativar e reativar cliente")
    class DeactivateReactivate {

        private UUID clientId;
        private Client activeClient;

        @BeforeEach
        void setUp() {
            clientId = UUID.randomUUID();
            activeClient = Client.builder()
                    .id(clientId)
                    .name("Ana Lima")
                    .phone("11999999999")
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        @Test
        @DisplayName("deve salvar cliente com active=false quando não há agendamentos futuros e force=false")
        void deactivate_withNoFutureAppointmentsAndForceFalse_savesWithActiveFalse() {
            given(clientAppointmentPort.findFutureActiveByClientId(any(UUID.class), any(LocalDate.class)))
                    .willReturn(Collections.emptyList());
            given(clientRepository.save(any(Client.class))).willAnswer(inv -> inv.getArgument(0));

            useCase.deactivate(activeClient, false);

            assertThat(activeClient.isActive()).isFalse();
        }

        @Test
        @DisplayName("deve lançar HasFutureAppointmentsException quando há agendamentos futuros e force=false")
        void deactivate_withFutureAppointmentsAndForceFalse_throwsHasFutureAppointmentsException() {
            AppointmentSummary summary = new AppointmentSummary(
                    UUID.randomUUID().toString(),
                    LocalDate.now().plusDays(3),
                    LocalTime.of(10, 0),
                    "Extensão de cílios");
            given(clientAppointmentPort.findFutureActiveByClientId(any(UUID.class), any(LocalDate.class)))
                    .willReturn(List.of(summary));

            assertThatThrownBy(() -> useCase.deactivate(activeClient, false))
                    .isInstanceOf(HasFutureAppointmentsException.class);

            then(clientRepository).should(never()).save(any());
        }

        @Test
        @DisplayName("deve cancelar agendamentos futuros e desativar o cliente quando force=true")
        void deactivate_withFutureAppointmentsAndForceTrue_ignoresAppointmentsAndSaves() {
            given(clientRepository.save(any(Client.class))).willAnswer(inv -> inv.getArgument(0));

            useCase.deactivate(activeClient, true);

            then(clientAppointmentPort).should(never()).findFutureActiveByClientId(any(), any());
            then(clientAppointmentPort).should().deleteFutureAppointmentsByClientId(any(), any());
            assertThat(activeClient.isActive()).isFalse();
        }

        @Test
        @DisplayName("deve salvar cliente com active=true ao reativar")
        void reactivate_withExistingClient_savesWithActiveTrue() {
            given(clientRepository.save(any(Client.class))).willAnswer(inv -> inv.getArgument(0));

            useCase.reactivate(activeClient);

            assertThat(activeClient.isActive()).isTrue();
        }
    }
}
