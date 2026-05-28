package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.Appointment;
import com.lashmanager.app.domain.model.AppointmentSummary;
import com.lashmanager.app.domain.port.in.CreateAppointmentUseCase;

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
    long countFutureActiveByClientId(UUID clientId, LocalDate today);
    long countFutureActiveByServiceId(UUID serviceId, LocalDate today);
    long countAllByClientId(UUID clientId);
    long countAllByServiceId(UUID serviceId);
    List<AppointmentSummary> findFutureActiveByClientId(UUID clientId, LocalDate today);
    List<AppointmentSummary> findFutureActiveByServiceId(UUID serviceId, LocalDate today);
    void unlinkClientFromPastAppointments(UUID clientId, LocalDate today);
    void deleteFutureAppointmentsByClientId(UUID clientId, LocalDate today);
}
