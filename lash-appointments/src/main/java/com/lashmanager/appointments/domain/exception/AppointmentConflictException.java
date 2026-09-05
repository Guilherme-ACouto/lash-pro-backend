package com.lashmanager.appointments.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;

public class AppointmentConflictException extends BusinessException {
  public AppointmentConflictException() {
    super("Já existe um agendamento neste horário.");
  }
}
