package com.lashmanager.appointments.application.usecase;

import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import java.math.BigDecimal;

public final class AppointmentUseCaseMapper {

  private AppointmentUseCaseMapper() {}

  public static CreateAppointmentUseCase.AppointmentResult toResult(
      Appointment a, String clientName, String serviceName, BigDecimal servicePrice) {
    return new CreateAppointmentUseCase.AppointmentResult(
        a.getId(),
        a.getClientId(),
        clientName,
        a.getServiceId(),
        serviceName,
        servicePrice,
        a.getScheduledDate().toString(),
        a.getScheduledTime().toString(),
        a.getDurationMinutes(),
        a.getStatus().name(),
        a.getNotes(),
        a.getFinancialEntryId() != null ? a.getFinancialEntryId().toString() : null,
        a.getCreatedAt().toString());
  }
}
