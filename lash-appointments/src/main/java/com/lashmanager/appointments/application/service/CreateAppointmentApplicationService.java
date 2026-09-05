package com.lashmanager.appointments.application.service;

import com.lashmanager.appointments.application.command.CreateAppointmentCommand;
import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAppointmentApplicationService {

  private final CreateAppointmentUseCase createAppointmentUseCase;

  public CreateAppointmentUseCase.AppointmentResult when(CreateAppointmentCommand command) {
    return createAppointmentUseCase.execute(command.toDomainCommand());
  }
}
