package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.clients.domain.model.AppointmentSummary;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.DeactivateClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientAppointmentPort;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeactivateClientUseCaseImpl implements DeactivateClientUseCase {

  private final ClientRepository clientRepository;
  private final ClientAppointmentPort clientAppointmentPort;

  @Override
  public void deactivate(UUID id, boolean force) {
    Client client =
        clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));

    if (!force) {
      List<AppointmentSummary> futureAppointments =
          clientAppointmentPort.findFutureActiveByClientId(id, LocalDate.now());
      if (!futureAppointments.isEmpty()) {
        throw new HasFutureAppointmentsException("cliente", futureAppointments);
      }
    } else {
      clientAppointmentPort.deleteFutureAppointmentsByClientId(id, LocalDate.now());
    }

    Client deactivated = client.toBuilder().active(false).updatedAt(LocalDateTime.now()).build();

    clientRepository.save(deactivated);
  }

  @Override
  public void reactivate(UUID id) {
    Client client =
        clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));

    Client reactivated = client.toBuilder().active(true).updatedAt(LocalDateTime.now()).build();

    clientRepository.save(reactivated);
  }
}
