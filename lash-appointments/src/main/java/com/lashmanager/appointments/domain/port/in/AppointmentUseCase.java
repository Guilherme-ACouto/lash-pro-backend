package com.lashmanager.appointments.domain.port.in;

import com.lashmanager.appointments.application.command.CreateAppointmentCommand;
import com.lashmanager.appointments.application.command.UpdateAppointmentCommand;
import com.lashmanager.appointments.domain.model.Appointment;

public interface AppointmentUseCase {

    Appointment create(CreateAppointmentCommand command);

    void update(Appointment appointment, UpdateAppointmentCommand command);

    void confirm(Appointment appointment);

    void complete(Appointment appointment, String paymentMethod);

    void cancel(Appointment appointment);

    void noShow(Appointment appointment);
}
