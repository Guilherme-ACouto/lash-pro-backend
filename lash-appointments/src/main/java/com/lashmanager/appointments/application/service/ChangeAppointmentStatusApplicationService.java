package com.lashmanager.appointments.application.service;

import com.lashmanager.appointments.application.command.CancelAppointmentCommand;
import com.lashmanager.appointments.application.command.CompleteAppointmentCommand;
import com.lashmanager.appointments.application.command.ConfirmAppointmentCommand;
import com.lashmanager.appointments.application.command.NoShowAppointmentCommand;
import com.lashmanager.appointments.domain.port.in.ChangeAppointmentStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeAppointmentStatusApplicationService {

  private final ChangeAppointmentStatusUseCase changeAppointmentStatusUseCase;

  public void when(ConfirmAppointmentCommand command) {
    changeAppointmentStatusUseCase.confirm(command.getId());
  }

  public void when(CompleteAppointmentCommand command) {
    changeAppointmentStatusUseCase.complete(command.getId(), command.getPaymentMethod());
  }

  public void when(CancelAppointmentCommand command) {
    changeAppointmentStatusUseCase.cancel(command.getId());
  }

  public void when(NoShowAppointmentCommand command) {
    changeAppointmentStatusUseCase.noShow(command.getId());
  }
}
