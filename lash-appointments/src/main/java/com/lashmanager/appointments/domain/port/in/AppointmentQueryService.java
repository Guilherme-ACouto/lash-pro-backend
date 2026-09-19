package com.lashmanager.appointments.domain.port.in;

import com.lashmanager.appointments.domain.model.AppointmentDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentQueryService {

    AppointmentDetails getById(UUID id);

    List<AppointmentDetails> listByDate(LocalDate date);

    List<AppointmentDetails> listByDateRange(LocalDate startDate, LocalDate endDate);
}
