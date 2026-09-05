package com.lashmanager.appointments.application.usecase;

import com.lashmanager.appointments.domain.exception.AppointmentConflictException;
import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.model.AppointmentStatus;
import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.appointments.domain.port.out.AppointmentRepository;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import com.lashmanager.core.domain.exception.BusinessException;
import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAppointmentUseCaseImpl implements CreateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public AppointmentResult execute(CreateAppointmentCommand command) {
        Client client = clientRepository
                .findById(command.clientId())
                .orElseThrow(() -> new BusinessException("Cliente não encontrado: " + command.clientId()));
        if (!client.isActive()) {
            throw new BusinessException("Cliente inativo");
        }

        ServiceOffering service = serviceRepository
                .findById(command.serviceId())
                .orElseThrow(() -> new BusinessException("Serviço não encontrado: " + command.serviceId()));
        if (!service.isActive()) {
            throw new BusinessException("Serviço inativo");
        }

        LocalTime start = command.scheduledTime();
        LocalTime end = start.plusMinutes(command.durationMinutes());
        if (start.isBefore(LocalTime.of(6, 0)) || end.isAfter(LocalTime.of(20, 0))) {
            throw new BusinessException("Horário fora do expediente (06:00–20:00)");
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
        return AppointmentUseCaseMapper.toResult(saved, client.getName(), service.getName(), service.getPrice());
    }
}
