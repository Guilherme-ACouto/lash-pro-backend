package com.lashmanager.appointments.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * O {@code id} não vem no corpo do JSON — vem do {@code @PathVariable} da URL, setado via
 * {@link #id(UUID)} pelo Resource antes de repassar pra ApplicationService.
 */
@Getter
@AllArgsConstructor
public class UpdateAppointmentCommand extends AbstractCommand {

    private UUID id;

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

    public UpdateAppointmentCommand id(UUID id) {
        this.id = id;
        return this;
    }
}
