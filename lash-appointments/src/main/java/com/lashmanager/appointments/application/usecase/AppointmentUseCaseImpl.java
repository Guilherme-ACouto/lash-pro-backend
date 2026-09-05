package com.lashmanager.appointments.application.usecase;

import com.lashmanager.appointments.application.command.CreateAppointmentCommand;
import com.lashmanager.appointments.application.command.UpdateAppointmentCommand;
import com.lashmanager.appointments.domain.exception.AppointmentConflictException;
import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.model.AppointmentStatus;
import com.lashmanager.appointments.domain.port.in.AppointmentUseCase;
import com.lashmanager.appointments.domain.port.out.AppointmentFinancialPort;
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
public class AppointmentUseCaseImpl implements AppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentFinancialPort financialPort;

    @Override
    public Appointment create(CreateAppointmentCommand command) {
        Client client = clientRepository
                .findById(command.getClientId())
                .orElseThrow(() -> new BusinessException("Cliente não encontrado: " + command.getClientId()));
        if (!client.isActive()) {
            throw new BusinessException("Cliente inativo");
        }

        ServiceOffering service = serviceRepository
                .findById(command.getServiceId())
                .orElseThrow(() -> new BusinessException("Serviço não encontrado: " + command.getServiceId()));
        if (!service.isActive()) {
            throw new BusinessException("Serviço inativo");
        }

        LocalTime start = command.getScheduledTime();
        LocalTime end = start.plusMinutes(command.getDurationMinutes());
        if (start.isBefore(LocalTime.of(6, 0)) || end.isAfter(LocalTime.of(20, 0))) {
            throw new BusinessException("Horário fora do expediente (06:00–20:00)");
        }

        List<Appointment> existing = appointmentRepository.findActiveByDate(command.getScheduledDate());
        boolean conflicts = existing.stream().anyMatch(a -> {
            LocalTime aEnd = a.getScheduledTime().plusMinutes(a.getDurationMinutes());
            LocalTime newEnd = command.getScheduledTime().plusMinutes(command.getDurationMinutes());
            return a.getScheduledTime().isBefore(newEnd) && aEnd.isAfter(command.getScheduledTime());
        });
        if (conflicts) {
            throw new AppointmentConflictException();
        }

        LocalDateTime now = LocalDateTime.now();
        Appointment appointment = Appointment.builder()
                .id(UUID.randomUUID())
                .clientId(command.getClientId())
                .serviceId(command.getServiceId())
                .scheduledDate(command.getScheduledDate())
                .scheduledTime(command.getScheduledTime())
                .durationMinutes(command.getDurationMinutes())
                .status(AppointmentStatus.SCHEDULED)
                .notes(command.getNotes())
                .createdAt(now)
                .updatedAt(now)
                .build();

        return appointmentRepository.save(appointment);
    }

    @Override
    public void update(Appointment appointment, UpdateAppointmentCommand command) {
        clientRepository
                .findById(command.getClientId())
                .orElseThrow(() -> new BusinessException("Cliente não encontrado: " + command.getClientId()));

        serviceRepository
                .findById(command.getServiceId())
                .orElseThrow(() -> new BusinessException("Serviço não encontrado: " + command.getServiceId()));

        appointment.update(command);
        appointmentRepository.save(appointment);
    }

    @Override
    public void confirm(Appointment appointment) {
        appointment.confirm();
        appointmentRepository.save(appointment);
    }

    @Override
    public void complete(Appointment appointment, String paymentMethod) {
        String clientName = appointment.getClientId() != null
                ? clientRepository.findById(appointment.getClientId()).map(Client::getName).orElse("Cliente")
                : "Cliente";
        ServiceOffering service = serviceRepository
                .findById(appointment.getServiceId())
                .orElseThrow(() -> new BusinessException("Serviço não encontrado"));

        UUID financialEntryId = financialPort.createIncomeEntry(
                appointment.getId(),
                service.getName() + " — " + clientName,
                service.getPrice(),
                appointment.getScheduledDate(),
                paymentMethod);

        appointment.complete(financialEntryId);
        appointmentRepository.save(appointment);
    }

    @Override
    public void cancel(Appointment appointment) {
        appointment.cancel();
        appointmentRepository.save(appointment);
    }

    @Override
    public void noShow(Appointment appointment) {
        appointment.noShow();
        appointmentRepository.save(appointment);
    }
}
