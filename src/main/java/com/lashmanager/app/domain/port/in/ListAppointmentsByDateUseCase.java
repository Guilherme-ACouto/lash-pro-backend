package com.lashmanager.app.domain.port.in;

import java.time.LocalDate;
import java.util.List;

public interface ListAppointmentsByDateUseCase {
    List<CreateAppointmentUseCase.AppointmentResult> execute(LocalDate date);
    List<CreateAppointmentUseCase.AppointmentResult> executeRange(LocalDate startDate, LocalDate endDate);
}
