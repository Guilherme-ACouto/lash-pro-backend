package com.lashmanager.clients.application.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;

import com.lashmanager.clients.domain.exception.ClientNotFoundException;
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
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Excluir cliente")
class DeleteClientUseCaseImplTest {

  @Mock private ClientRepository clientRepository;

  @Mock private ClientAppointmentPort clientAppointmentPort;

  private DeleteClientUseCaseImpl useCase;
  private UUID clientId;
  private Client existingClient;

  @BeforeEach
  void setUp() {
    useCase = new DeleteClientUseCaseImpl(clientRepository, clientAppointmentPort);
    clientId = UUID.randomUUID();
    existingClient =
        Client.builder()
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
  void execute_withNoFutureAppointments_deletesInCorrectOrder() {
    given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));
    given(clientAppointmentPort.findFutureActiveByClientId(eq(clientId), any()))
        .willReturn(Collections.emptyList());

    useCase.execute(clientId);

    InOrder inOrder = inOrder(clientAppointmentPort, clientRepository);
    inOrder.verify(clientAppointmentPort).unlinkClientFromPastAppointments(eq(clientId), any());
    inOrder.verify(clientRepository).deleteById(clientId);
  }

  @Test
  @DisplayName("deve lançar ClientNotFoundException quando o id não existe")
  void execute_withUnknownId_throwsClientNotFoundException() {
    given(clientRepository.findById(any())).willReturn(Optional.empty());

    assertThatThrownBy(() -> useCase.execute(clientId)).isInstanceOf(ClientNotFoundException.class);

    then(clientAppointmentPort).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName(
      "deve lançar HasFutureAppointmentsException quando o cliente possui agendamentos futuros ativos")
  void execute_withFutureActiveAppointments_throwsHasFutureAppointmentsException() {
    given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));

    AppointmentSummary summary1 =
        new AppointmentSummary(
            UUID.randomUUID().toString(),
            LocalDate.now().plusDays(3),
            LocalTime.of(10, 0),
            "Extensão de Cílios");
    AppointmentSummary summary2 =
        new AppointmentSummary(
            UUID.randomUUID().toString(),
            LocalDate.now().plusDays(7),
            LocalTime.of(14, 30),
            "Manutenção");

    given(clientAppointmentPort.findFutureActiveByClientId(eq(clientId), any()))
        .willReturn(List.of(summary1, summary2));

    assertThatThrownBy(() -> useCase.execute(clientId))
        .isInstanceOf(HasFutureAppointmentsException.class);

    then(clientRepository).should(never()).deleteById(any());
  }
}
