package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.Appointment;
import com.lashmanager.app.domain.port.in.CreateAppointmentUseCase.AppointmentResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AppointmentUseCaseMapper {

    public AppointmentResult toResult(Appointment a, String clientName, String serviceName, BigDecimal servicePrice) {
        return new AppointmentResult(
                a.getId(),
                a.getClientId(),
                clientName,
                a.getServiceId(),
                serviceName,
                servicePrice,
                a.getScheduledDate().toString(),
                a.getScheduledTime().toString(),
                a.getDurationMinutes(),
                a.getStatus().name(),
                a.getNotes(),
                a.getFinancialEntryId() != null ? a.getFinancialEntryId().toString() : null,
                a.getCreatedAt().toString()
        );
    }
}
