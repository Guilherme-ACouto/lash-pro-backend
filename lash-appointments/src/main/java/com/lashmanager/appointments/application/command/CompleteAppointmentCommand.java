package com.lashmanager.appointments.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * O {@code id} vem do {@code @PathVariable}, setado via {@link #id(UUID)} — só
 * {@code paymentMethod} vem do corpo (opcional, endpoint aceita corpo ausente).
 */
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
