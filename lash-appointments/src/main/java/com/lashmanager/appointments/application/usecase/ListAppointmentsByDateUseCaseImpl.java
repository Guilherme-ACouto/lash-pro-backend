package com.lashmanager.appointments.application.usecase;

import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.appointments.domain.port.in.ListAppointmentsByDateUseCase;
import com.lashmanager.appointments.domain.port.out.AppointmentRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class ListAppointmentsByDateUseCaseImpl implements ListAppointmentsByDateUseCase {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<CreateAppointmentUseCase.AppointmentResult> execute(LocalDate date) {
        return appointmentRepository.findByDateWithDetails(date);
    }

    @Override
    public List<CreateAppointmentUseCase.AppointmentResult> executeRange(LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findByDateRangeWithDetails(startDate, endDate);
    }
}
