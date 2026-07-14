package com.lashmanager.appointments.domain.port.out;

import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.clients.domain.model.AppointmentSummary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(UUID id);
    List<Appointment> findActiveByDate(LocalDate date);
    List<CreateAppointmentUseCase.AppointmentResult> findByDateWithDetails(LocalDate date);
    List<CreateAppointmentUseCase.AppointmentResult> findByDateRangeWithDetails(LocalDate startDate, LocalDate endDate);
    List<AppointmentSummary> findFutureActiveByClientId(UUID clientId, LocalDate from);
    void deleteFutureAppointmentsByClientId(UUID clientId, LocalDate from);
    void unlinkClientFromPastAppointments(UUID clientId, LocalDate from);
    boolean hasActiveAppointmentsByServiceId(UUID serviceId, LocalDate from);
}
