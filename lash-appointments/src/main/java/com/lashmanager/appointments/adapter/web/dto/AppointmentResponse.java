package com.lashmanager.appointments.adapter.web.dto;

import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import java.math.BigDecimal;
import java.util.UUID;

public record AppointmentResponse(
        UUID id,
        UUID clientId,
        String clientName,
        UUID serviceId,
        String serviceName,
        BigDecimal servicePrice,
        String scheduledDate,
        String scheduledTime,
        int durationMinutes,
        String status,
        String notes,
        String financialEntryId,
        String createdAt) {
    public static AppointmentResponse from(CreateAppointmentUseCase.AppointmentResult r) {
        return new AppointmentResponse(
                r.id(),
                r.clientId(),
                r.clientName(),
                r.serviceId(),
                r.serviceName(),
                r.servicePrice(),
                r.scheduledDate(),
                r.scheduledTime(),
                r.durationMinutes(),
                r.status(),
                r.notes(),
                r.financialEntryId(),
                r.createdAt());
    }
}
