package com.lashmanager.app.domain.port.in;

import java.util.UUID;

public interface UpdateAppointmentUseCase {
    CreateAppointmentUseCase.AppointmentResult execute(UUID id, CreateAppointmentUseCase.CreateAppointmentCommand command);
}
