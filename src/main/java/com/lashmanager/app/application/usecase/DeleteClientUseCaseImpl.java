package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.app.domain.model.AppointmentSummary;
import com.lashmanager.app.domain.port.in.DeleteClientUseCase;
import com.lashmanager.app.domain.port.out.AppointmentRepository;
import com.lashmanager.app.domain.port.out.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteClientUseCaseImpl implements DeleteClientUseCase {

    private final ClientRepository clientRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public void execute(UUID id) {
        clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));

        List<AppointmentSummary> futureAppointments =
                appointmentRepository.findFutureActiveByClientId(id, LocalDate.now());

        if (!futureAppointments.isEmpty()) {
            throw new HasFutureAppointmentsException("cliente", futureAppointments);
        }

        appointmentRepository.deleteFutureAppointmentsByClientId(id, LocalDate.now());
        appointmentRepository.unlinkClientFromPastAppointments(id, LocalDate.now());
        clientRepository.deleteById(id);
    }
}
