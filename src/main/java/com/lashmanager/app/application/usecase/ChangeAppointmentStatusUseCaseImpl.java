package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.AppointmentNotFoundException;
import com.lashmanager.app.domain.exception.DomainException;
import com.lashmanager.app.domain.model.Appointment;
import com.lashmanager.app.domain.model.AppointmentStatus;
import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.model.FinancialEntry;
import com.lashmanager.app.domain.model.FinancialEntryStatus;
import com.lashmanager.app.domain.model.FinancialEntryType;
import com.lashmanager.app.domain.model.Service;
import com.lashmanager.app.domain.port.in.ChangeAppointmentStatusUseCase;
import com.lashmanager.app.domain.port.out.AppointmentRepository;
import com.lashmanager.app.domain.port.out.ClientRepository;
import com.lashmanager.app.domain.port.out.FinancialEntryRepository;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional
public class ChangeAppointmentStatusUseCaseImpl implements ChangeAppointmentStatusUseCase {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final FinancialEntryRepository financialEntryRepository;

    @Override
    public void confirm(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new DomainException("Agendamento não pode ser confirmado no status atual");
        }

        Appointment confirmed = Appointment.builder()
                .id(appointment.getId())
                .clientId(appointment.getClientId())
                .serviceId(appointment.getServiceId())
                .scheduledDate(appointment.getScheduledDate())
                .scheduledTime(appointment.getScheduledTime())
                .durationMinutes(appointment.getDurationMinutes())
                .status(AppointmentStatus.CONFIRMED)
                .notes(appointment.getNotes())
                .financialEntryId(appointment.getFinancialEntryId())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        appointmentRepository.save(confirmed);
    }

    @Override
    public void complete(UUID id, String paymentMethod) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new DomainException("Agendamento não pode ser concluído no status atual");
        }

        Client client = clientRepository.findById(appointment.getClientId())
                .orElseThrow(() -> new DomainException("Cliente não encontrado"));
        Service service = serviceRepository.findById(appointment.getServiceId())
                .orElseThrow(() -> new DomainException("Serviço não encontrado"));

        FinancialEntry entry = FinancialEntry.builder()
                .id(UUID.randomUUID())
                .type(FinancialEntryType.INCOME)
                .description(service.getName() + " — " + client.getName())
                .amount(service.getPrice())
                .dueDate(appointment.getScheduledDate())
                .paymentDate(appointment.getScheduledDate())
                .status(FinancialEntryStatus.PAID)
                .appointmentId(appointment.getId())
                .category("Serviço")
                .paymentMethod(paymentMethod)
                .createdAt(LocalDateTime.now())
                .build();

        FinancialEntry savedEntry = financialEntryRepository.save(entry);

        Appointment completed = Appointment.builder()
                .id(appointment.getId())
                .clientId(appointment.getClientId())
                .serviceId(appointment.getServiceId())
                .scheduledDate(appointment.getScheduledDate())
                .scheduledTime(appointment.getScheduledTime())
                .durationMinutes(appointment.getDurationMinutes())
                .status(AppointmentStatus.COMPLETED)
                .notes(appointment.getNotes())
                .financialEntryId(savedEntry.getId())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        appointmentRepository.save(completed);
    }

    @Override
    public void cancel(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED
                || appointment.getStatus() == AppointmentStatus.CANCELLED
                || appointment.getStatus() == AppointmentStatus.NO_SHOW) {
            throw new DomainException("Agendamento não pode ser cancelado no status atual");
        }

        Appointment cancelled = Appointment.builder()
                .id(appointment.getId())
                .clientId(appointment.getClientId())
                .serviceId(appointment.getServiceId())
                .scheduledDate(appointment.getScheduledDate())
                .scheduledTime(appointment.getScheduledTime())
                .durationMinutes(appointment.getDurationMinutes())
                .status(AppointmentStatus.CANCELLED)
                .notes(appointment.getNotes())
                .financialEntryId(appointment.getFinancialEntryId())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        appointmentRepository.save(cancelled);
    }

    @Override
    public void noShow(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED
                && appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new DomainException("Agendamento não pode ser marcado como não compareceu no status atual");
        }

        Appointment noShow = Appointment.builder()
                .id(appointment.getId())
                .clientId(appointment.getClientId())
                .serviceId(appointment.getServiceId())
                .scheduledDate(appointment.getScheduledDate())
                .scheduledTime(appointment.getScheduledTime())
                .durationMinutes(appointment.getDurationMinutes())
                .status(AppointmentStatus.NO_SHOW)
                .notes(appointment.getNotes())
                .financialEntryId(appointment.getFinancialEntryId())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        appointmentRepository.save(noShow);
    }
}
