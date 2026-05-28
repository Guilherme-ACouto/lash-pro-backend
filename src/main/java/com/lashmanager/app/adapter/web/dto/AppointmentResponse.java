package com.lashmanager.app.adapter.web.dto;

import java.math.BigDecimal;

public record AppointmentResponse(
        String id,
        String clientId,
        String clientName,
        String serviceId,
        String serviceName,
        BigDecimal servicePrice,
        String scheduledDate,
        String scheduledTime,
        int durationMinutes,
        String status,
        String notes,
        String financialEntryId,
        String createdAt
) {}
