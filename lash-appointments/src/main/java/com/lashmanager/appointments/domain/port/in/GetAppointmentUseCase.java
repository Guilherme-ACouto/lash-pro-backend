package com.lashmanager.appointments.domain.port.in;

import java.util.UUID;

public interface GetAppointmentUseCase {
  CreateAppointmentUseCase.AppointmentResult execute(UUID id);
}
