package com.lashmanager.appointments.application.command;

import com.lashmanager.appointments.domain.port.in.UpdateAppointmentUseCase;
import com.lashmanager.core.infrastructure.command.AbstractCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppointmentCommand extends AbstractCommand {

    @NotNull
    private UUID id;

    private UUID clientId;

    @NotNull
    private UUID serviceId;

    @NotNull
    private LocalDate scheduledDate;

    @NotNull
    private LocalTime scheduledTime;

    @Min(1)
    private int durationMinutes;

    private String notes;

    public UpdateAppointmentUseCase.UpdateAppointmentCommand toDomainCommand() {
        return new UpdateAppointmentUseCase.UpdateAppointmentCommand(
                clientId, serviceId, scheduledDate, scheduledTime, durationMinutes, notes);
    }
}
