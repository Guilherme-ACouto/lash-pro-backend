package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.app.domain.exception.ServiceNotFoundException;
import com.lashmanager.app.domain.model.AppointmentSummary;
import com.lashmanager.app.domain.model.Service;
import com.lashmanager.app.domain.port.in.DeactivateServiceUseCase;
import com.lashmanager.app.domain.port.out.AppointmentRepository;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class DeactivateServiceUseCaseImpl implements DeactivateServiceUseCase {

    private final ServiceRepository serviceRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public void deactivate(UUID id, boolean force) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        if (!force) {
            List<AppointmentSummary> futureAppointments =
                    appointmentRepository.findFutureActiveByServiceId(id, LocalDate.now());
            if (!futureAppointments.isEmpty()) {
                throw new HasFutureAppointmentsException("serviço", futureAppointments);
            }
        }

        log.info("Desativando serviço: {} ({})", service.getName(), id);
        serviceRepository.save(withActive(service, false));
    }

    @Override
    public void reactivate(UUID id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        log.info("Reativando serviço: {} ({})", service.getName(), id);
        serviceRepository.save(withActive(service, true));
    }

    private Service withActive(Service service, boolean active) {
        return Service.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .durationMinutes(service.getDurationMinutes())
                .active(active)
                .createdAt(service.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
