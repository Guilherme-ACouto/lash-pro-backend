package com.bravapro.appointments.domain.model;

import com.bravapro.appointments.application.command.CreateAppointmentCommand;
import com.bravapro.appointments.application.command.UpdateAppointmentCommand;
import com.bravapro.core.domain.exception.BusinessException;
import com.bravapro.core.domain.model.DomainEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements DomainEntity {
    private UUID id;
    private UUID clientId;
    private UUID serviceId;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private int durationMinutes;
    private AppointmentStatus status;
    private String notes;
    private UUID financialEntryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Appointment schedule(CreateAppointmentCommand command) {
        assertBusinessHours(command.getScheduledTime(), command.getDurationMinutes());

        LocalDateTime now = LocalDateTime.now();
        return Appointment.builder()
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
    }

    private static void assertBusinessHours(LocalTime start, int durationMinutes) {
        LocalTime end = start.plusMinutes(durationMinutes);
        if (start.isBefore(LocalTime.of(6, 0)) || end.isAfter(LocalTime.of(20, 0))) {
            throw new BusinessException("Horário fora do expediente (06:00–20:00)");
        }
    }

    public boolean overlaps(Appointment other) {
        LocalTime thisEnd = this.scheduledTime.plusMinutes(this.durationMinutes);
        LocalTime otherEnd = other.scheduledTime.plusMinutes(other.durationMinutes);
        return this.scheduledTime.isBefore(otherEnd) && thisEnd.isAfter(other.scheduledTime);
    }

    public void update(UpdateAppointmentCommand command) {
        this.clientId = command.getClientId();
        this.serviceId = command.getServiceId();
        this.scheduledDate = command.getScheduledDate();
        this.scheduledTime = command.getScheduledTime();
        this.durationMinutes = command.getDurationMinutes();
        this.notes = command.getNotes();
        this.updatedAt = LocalDateTime.now();
    }

    public void confirm() {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new BusinessException("Agendamento não pode ser confirmado no status atual");
        }
        this.status = AppointmentStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void complete(UUID financialEntryId) {
        if (status != AppointmentStatus.CONFIRMED) {
            throw new BusinessException("Agendamento não pode ser concluído no status atual");
        }
        this.status = AppointmentStatus.COMPLETED;
        this.financialEntryId = financialEntryId;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status == AppointmentStatus.COMPLETED
                || status == AppointmentStatus.CANCELLED
                || status == AppointmentStatus.NO_SHOW) {
            throw new BusinessException("Agendamento não pode ser cancelado no status atual");
        }
        this.status = AppointmentStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public void noShow() {
        if (status != AppointmentStatus.CONFIRMED && status != AppointmentStatus.SCHEDULED) {
            throw new BusinessException("Agendamento não pode ser marcado como não compareceu no status atual");
        }
        this.status = AppointmentStatus.NO_SHOW;
        this.updatedAt = LocalDateTime.now();
    }
}
