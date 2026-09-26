package com.bravapro.appointments.application.command;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.command.CommandPermission;
import com.bravapro.core.infrastructure.command.AbstractCommand;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@CommandPermission(Permission.APPOINTMENT_CREATE)
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
