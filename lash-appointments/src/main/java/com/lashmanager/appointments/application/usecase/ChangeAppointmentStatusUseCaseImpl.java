package com.lashmanager.appointments.application.usecase;

import com.lashmanager.appointments.domain.exception.AppointmentNotFoundException;
import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.model.AppointmentStatus;
import com.lashmanager.appointments.domain.port.in.ChangeAppointmentStatusUseCase;
import com.lashmanager.appointments.domain.port.out.AppointmentFinancialPort;
import com.lashmanager.appointments.domain.port.out.AppointmentRepository;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import com.lashmanager.core.domain.exception.BusinessException;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangeAppointmentStatusUseCaseImpl implements ChangeAppointmentStatusUseCase {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentFinancialPort financialPort;

    @Override
    public void confirm(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new BusinessException("Agendamento não pode ser confirmado no status atual");
        }

        appointmentRepository.save(appointment.toBuilder()
                .status(AppointmentStatus.CONFIRMED)
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public void complete(UUID id, String paymentMethod) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new BusinessException("Agendamento não pode ser concluído no status atual");
        }

        String clientName = appointment.getClientId() != null
                ? clientRepository.findById(appointment.getClientId()).map(c -> c.getName()).orElse("Cliente")
                : "Cliente";
        String serviceName = serviceRepository.findById(appointment.getServiceId())
                .map(s -> s.getName()).orElse("Serviço");
        var servicePrice = serviceRepository.findById(appointment.getServiceId())
                .map(s -> s.getPrice()).orElseThrow(() -> new BusinessException("Serviço não encontrado"));

        UUID financialEntryId = financialPort.createIncomeEntry(
                appointment.getId(),
                serviceName + " — " + clientName,
                servicePrice,
                appointment.getScheduledDate(),
                paymentMethod
        );

        appointmentRepository.save(appointment.toBuilder()
                .status(AppointmentStatus.COMPLETED)
                .financialEntryId(financialEntryId)
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public void cancel(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED
                || appointment.getStatus() == AppointmentStatus.CANCELLED
                || appointment.getStatus() == AppointmentStatus.NO_SHOW) {
            throw new BusinessException("Agendamento não pode ser cancelado no status atual");
        }

        appointmentRepository.save(appointment.toBuilder()
                .status(AppointmentStatus.CANCELLED)
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public void noShow(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED
                && appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new BusinessException("Agendamento não pode ser marcado como não compareceu no status atual");
        }

        appointmentRepository.save(appointment.toBuilder()
                .status(AppointmentStatus.NO_SHOW)
                .updatedAt(LocalDateTime.now())
                .build());
    }
}
