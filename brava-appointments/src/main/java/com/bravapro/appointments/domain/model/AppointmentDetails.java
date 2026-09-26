package com.bravapro.appointments.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record AppointmentDetails(
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
        String createdAt) {}
