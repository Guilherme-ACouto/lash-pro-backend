package com.lashmanager.appointments.domain.port.in;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface UpdateAppointmentUseCase {

    record UpdateAppointmentCommand(
            UUID clientId,
            UUID serviceId,
            LocalDate scheduledDate,
            LocalTime scheduledTime,
            int durationMinutes,
            String notes) {}

    CreateAppointmentUseCase.AppointmentResult execute(UUID id, UpdateAppointmentCommand command);
}
