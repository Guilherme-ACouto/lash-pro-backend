package com.bravapro.appointments.application.command;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.command.CommandPermission;
import com.bravapro.core.infrastructure.command.AbstractCommand;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@CommandPermission(Permission.APPOINTMENT_UPDATE)
@Getter
@AllArgsConstructor
public class CompleteAppointmentCommand extends AbstractCommand {

    private UUID id;
    private final String paymentMethod;

    public CompleteAppointmentCommand id(UUID id) {
        this.id = id;
        return this;
    }
}
