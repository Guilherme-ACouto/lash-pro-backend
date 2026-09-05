package com.lashmanager.appointments.application.usecase;

import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.appointments.domain.port.in.ListAppointmentsByDateUseCase;
import com.lashmanager.appointments.domain.port.out.AppointmentQueryRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListAppointmentsByDateUseCaseImpl implements ListAppointmentsByDateUseCase {

    private final AppointmentQueryRepository appointmentQueryRepository;

    @Override
    public List<CreateAppointmentUseCase.AppointmentResult> execute(LocalDate date) {
        return appointmentQueryRepository.findByDateWithDetails(date);
    }

    @Override
    public List<CreateAppointmentUseCase.AppointmentResult> executeRange(LocalDate startDate, LocalDate endDate) {
        return appointmentQueryRepository.findByDateRangeWithDetails(startDate, endDate);
    }
}
