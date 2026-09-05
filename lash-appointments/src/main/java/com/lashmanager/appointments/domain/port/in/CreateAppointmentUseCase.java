package com.lashmanager.appointments.domain.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface CreateAppointmentUseCase {

    record CreateAppointmentCommand(
            UUID clientId,
            UUID serviceId,
            LocalDate scheduledDate,
            LocalTime scheduledTime,
            int durationMinutes,
            String notes) {}

    record AppointmentResult(
            UUID id,
            UUID clientId,
            String clientName,
            UUID serviceId,
            String serviceName,
            BigDecimal servicePrice,
            String scheduledDate,
            String scheduledTime,
            int durationMinutes,
            String status,
            String notes,
            String financialEntryId,
            String createdAt) {}

    AppointmentResult execute(CreateAppointmentCommand command);
}
