package com.lashmanager.appointments.application.service;

import com.lashmanager.appointments.application.command.UpdateAppointmentCommand;
import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.appointments.domain.port.in.UpdateAppointmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAppointmentApplicationService {

    private final UpdateAppointmentUseCase updateAppointmentUseCase;

    public CreateAppointmentUseCase.AppointmentResult when(UpdateAppointmentCommand command) {
        return updateAppointmentUseCase.execute(command.getId(), command.toDomainCommand());
    }
}
