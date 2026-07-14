package com.lashmanager.appointments.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;

import java.util.UUID;

public class AppointmentNotFoundException extends BusinessException {
    public AppointmentNotFoundException(UUID id) {
        super("Agendamento não encontrado: " + id);
    }
}
