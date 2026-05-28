package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.AppointmentConflictException;
import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.exception.DomainException;
import com.lashmanager.app.domain.exception.ServiceNotFoundException;
import com.lashmanager.app.domain.model.Appointment;
import com.lashmanager.app.domain.model.AppointmentStatus;
import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.model.Service;
import com.lashmanager.app.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.app.domain.port.out.AppointmentRepository;
import com.lashmanager.app.domain.port.out.ClientRepository;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CreateAppointmentUseCaseImpl implements CreateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentUseCaseMapper mapper;

    @Override
    public AppointmentResult execute(CreateAppointmentCommand command) {
        Client client = clientRepository.findById(command.clientId())
                .orElseThrow(() -> new ClientNotFoundException(command.clientId()));
        if (!client.isActive()) {
            throw new DomainException("Cliente inativo");
        }

        Service service = serviceRepository.findById(command.serviceId())
                .orElseThrow(() -> new ServiceNotFoundException(command.serviceId()));
        if (!service.isActive()) {
            throw new DomainException("Serviço inativo");
        }

        LocalTime start = command.scheduledTime();
        LocalTime end = start.plusMinutes(command.durationMinutes());
        if (start.isBefore(LocalTime.of(6, 0)) || end.isAfter(LocalTime.of(20, 0))) {
            throw new DomainException("Horário fora do expediente (06:00–20:00)");
        }

        List<Appointment> existing = appointmentRepository.findActiveByDate(command.scheduledDate());
        boolean conflicts = existing.stream().anyMatch(a -> {
            LocalTime aEnd = a.getScheduledTime().plusMinutes(a.getDurationMinutes());
            LocalTime newEnd = command.scheduledTime().plusMinutes(command.durationMinutes());
            return a.getScheduledTime().isBefore(newEnd) && aEnd.isAfter(command.scheduledTime());
        });
        if (conflicts) {
            throw new AppointmentConflictException();
        }

        LocalDateTime now = LocalDateTime.now();
        Appointment appointment = Appointment.builder()
                .id(UUID.randomUUID())
                .clientId(command.clientId())
                .serviceId(command.serviceId())
                .scheduledDate(command.scheduledDate())
                .scheduledTime(command.scheduledTime())
                .durationMinutes(command.durationMinutes())
                .status(AppointmentStatus.SCHEDULED)
                .notes(command.notes())
                .createdAt(now)
                .updatedAt(now)
                .build();

        Appointment saved = appointmentRepository.save(appointment);
        return mapper.toResult(saved, client.getName(), service.getName(), service.getPrice());
    }
}
