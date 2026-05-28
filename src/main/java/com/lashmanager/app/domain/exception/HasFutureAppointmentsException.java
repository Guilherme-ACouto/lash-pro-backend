package com.lashmanager.app.domain.exception;

import com.lashmanager.app.domain.model.AppointmentSummary;

import java.util.List;

public class HasFutureAppointmentsException extends DomainException {

    private final List<AppointmentSummary> appointments;

    public HasFutureAppointmentsException(String entity, List<AppointmentSummary> appointments) {
        super("Este(a) " + entity + " possui " + appointments.size() + " agendamento(s) futuro(s).");
        this.appointments = appointments;
    }

    public List<AppointmentSummary> getAppointments() {
        return appointments;
    }
}
