package com.lashmanager.app.domain.exception;

public class AppointmentConflictException extends DomainException {
    public AppointmentConflictException() {
        super("Horário indisponível — já existe um agendamento neste período");
    }
}
