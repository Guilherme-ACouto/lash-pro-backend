package com.bravapro.appointments.application.service;

import com.bravapro.appointments.application.command.CancelAppointmentCommand;
import com.bravapro.appointments.application.command.CompleteAppointmentCommand;
import com.bravapro.appointments.application.command.ConfirmAppointmentCommand;
import com.bravapro.appointments.application.command.CreateAppointmentCommand;
import com.bravapro.appointments.application.command.NoShowAppointmentCommand;
import com.bravapro.appointments.application.command.UpdateAppointmentCommand;
import com.bravapro.appointments.domain.exception.AppointmentNotFoundException;
import com.bravapro.appointments.domain.model.Appointment;
import com.bravapro.appointments.domain.port.in.AppointmentUseCase;
import com.bravapro.appointments.domain.port.out.AppointmentRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentApplicationService {

    private final AppointmentUseCase appointmentUseCase;
    private final AppointmentRepository appointmentRepository;

    public Appointment when(CreateAppointmentCommand command) {
        return appointmentUseCase.create(command);
    }

    public void when(UpdateAppointmentCommand command) {
        appointmentUseCase.update(getOne(command.getId()), command);
    }

    public void when(ConfirmAppointmentCommand command) {
        appointmentUseCase.confirm(getOne(command.getId()));
    }

    public void when(CompleteAppointmentCommand command) {
        appointmentUseCase.complete(getOne(command.getId()), command.getPaymentMethod());
    }

    public void when(CancelAppointmentCommand command) {
        appointmentUseCase.cancel(getOne(command.getId()));
    }

    public void when(NoShowAppointmentCommand command) {
        appointmentUseCase.noShow(getOne(command.getId()));
    }

    private Appointment getOne(UUID id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException(id));
    }
}
