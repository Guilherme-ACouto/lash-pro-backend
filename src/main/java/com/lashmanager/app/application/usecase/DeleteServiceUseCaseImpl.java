package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.app.domain.exception.ServiceNotFoundException;
import com.lashmanager.app.domain.model.AppointmentSummary;
import com.lashmanager.app.domain.port.in.DeleteServiceUseCase;
import com.lashmanager.app.domain.port.out.AppointmentRepository;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteServiceUseCaseImpl implements DeleteServiceUseCase {

    private final ServiceRepository serviceRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public void execute(UUID id) {
        serviceRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException(id));

        List<AppointmentSummary> futureAppointments =
                appointmentRepository.findFutureActiveByServiceId(id, LocalDate.now());

        if (!futureAppointments.isEmpty()) {
            throw new HasFutureAppointmentsException("serviço", futureAppointments);
        }

        serviceRepository.deleteById(id);
    }
}
