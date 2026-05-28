package com.lashmanager.app.domain.exception;

import java.util.UUID;

public class AppointmentNotFoundException extends DomainException {
    public AppointmentNotFoundException(UUID id) {
        super("Agendamento não encontrado: " + id);
    }
}
