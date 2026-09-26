package com.bravapro.appointments.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;

public class AppointmentConflictException extends BusinessException {
    public AppointmentConflictException() {
        super("Já existe um agendamento neste horário.");
    }
}
