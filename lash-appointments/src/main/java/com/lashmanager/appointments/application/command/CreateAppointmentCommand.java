package com.lashmanager.appointments.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAppointmentCommand extends AbstractCommand {

    private final UUID clientId;

    @NotNull
    private final UUID serviceId;

    @NotNull
    private final LocalDate scheduledDate;

    @NotNull
    private final LocalTime scheduledTime;

    @Min(1)
    private final int durationMinutes;

    private final String notes;
}
