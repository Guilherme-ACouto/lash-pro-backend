package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.clients.domain.model.AppointmentSummary;
import com.lashmanager.clients.domain.port.in.DeleteClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientAppointmentPort;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteClientUseCaseImpl implements DeleteClientUseCase {

    private final ClientRepository clientRepository;
    private final ClientAppointmentPort clientAppointmentPort;

    @Override
    public void execute(UUID id) {
        if (!clientRepository.findById(id).isPresent()) {
            throw new ClientNotFoundException(id);
        }

        List<AppointmentSummary> futureAppointments =
                clientAppointmentPort.findFutureActiveByClientId(id, LocalDate.now());

        if (!futureAppointments.isEmpty()) {
            throw new HasFutureAppointmentsException("cliente", futureAppointments);
        }

        clientAppointmentPort.unlinkClientFromPastAppointments(id, LocalDate.now());
        clientRepository.deleteById(id);
    }
}
