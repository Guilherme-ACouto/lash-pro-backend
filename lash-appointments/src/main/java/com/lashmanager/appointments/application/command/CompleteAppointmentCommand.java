package com.lashmanager.appointments.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
