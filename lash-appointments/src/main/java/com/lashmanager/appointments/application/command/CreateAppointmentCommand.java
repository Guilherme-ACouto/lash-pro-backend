package com.lashmanager.appointments.application.command;

import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.core.infrastructure.command.AbstractCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentCommand extends AbstractCommand {

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

    public CreateAppointmentUseCase.CreateAppointmentCommand toDomainCommand() {
        return new CreateAppointmentUseCase.CreateAppointmentCommand(
                clientId, serviceId, scheduledDate, scheduledTime, durationMinutes, notes);
    }
}
