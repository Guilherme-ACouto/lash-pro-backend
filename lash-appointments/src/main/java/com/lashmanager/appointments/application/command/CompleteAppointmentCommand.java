package com.lashmanager.appointments.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteAppointmentCommand extends AbstractCommand {
    @NotNull
    private UUID id;

    private String paymentMethod;
}
