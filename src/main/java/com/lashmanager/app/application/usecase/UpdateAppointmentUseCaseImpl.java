package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.AppointmentConflictException;
import com.lashmanager.app.domain.exception.AppointmentNotFoundException;
import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.exception.DomainException;
import com.lashmanager.app.domain.exception.ServiceNotFoundException;
import com.lashmanager.app.domain.model.Appointment;
import com.lashmanager.app.domain.model.AppointmentStatus;
import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.model.Service;
import com.lashmanager.app.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.app.domain.port.in.UpdateAppointmentUseCase;
import com.lashmanager.app.domain.port.out.AppointmentRepository;
import com.lashmanager.app.domain.port.out.ClientRepository;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class UpdateAppointmentUseCaseImpl implements UpdateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentUseCaseMapper mapper;

    @Override
    public CreateAppointmentUseCase.AppointmentResult execute(UUID id, CreateAppointmentUseCase.CreateAppointmentCommand command) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (existing.getStatus() == AppointmentStatus.COMPLETED || existing.getStatus() == AppointmentStatus.CANCELLED) {
            throw new DomainException("Não é possível editar agendamento com status " + existing.getStatus().name());
        }

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

        List<Appointment> activeAppointments = appointmentRepository.findActiveByDate(command.scheduledDate())
                .stream()
                .filter(a -> !a.getId().equals(id))
                .toList();

        boolean conflicts = activeAppointments.stream().anyMatch(a -> {
            LocalTime aEnd = a.getScheduledTime().plusMinutes(a.getDurationMinutes());
            LocalTime newEnd = command.scheduledTime().plusMinutes(command.durationMinutes());
            return a.getScheduledTime().isBefore(newEnd) && aEnd.isAfter(command.scheduledTime());
        });
        if (conflicts) {
            throw new AppointmentConflictException();
        }

        Appointment updated = Appointment.builder()
                .id(existing.getId())
                .clientId(command.clientId())
                .serviceId(command.serviceId())
                .scheduledDate(command.scheduledDate())
                .scheduledTime(command.scheduledTime())
                .durationMinutes(command.durationMinutes())
                .status(existing.getStatus())
                .notes(command.notes())
                .financialEntryId(existing.getFinancialEntryId())
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        Appointment saved = appointmentRepository.save(updated);
        return mapper.toResult(saved, client.getName(), service.getName(), service.getPrice());
    }
}
