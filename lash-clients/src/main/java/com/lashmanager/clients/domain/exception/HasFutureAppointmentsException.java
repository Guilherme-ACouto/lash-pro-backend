package com.lashmanager.clients.domain.exception;

import com.lashmanager.clients.domain.model.AppointmentSummary;
import com.lashmanager.core.domain.exception.BusinessException;
import java.util.List;

public class HasFutureAppointmentsException extends BusinessException {

  private final List<AppointmentSummary> appointments;

  public HasFutureAppointmentsException(String entity, List<AppointmentSummary> appointments) {
    super("Este(a) " + entity + " possui " + appointments.size() + " agendamento(s) futuro(s).");
    this.appointments = appointments;
  }

  public List<AppointmentSummary> getAppointments() {
    return appointments;
  }
}
