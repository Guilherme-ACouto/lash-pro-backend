package com.lashmanager.app.adapter.web.dto;

import java.math.BigDecimal;

public record FinancialEntryResponse(
        String id,
        String type,
        String expenseType,
        String description,
        BigDecimal amount,
        String dueDate,
        String paymentDate,
        String status,
        String category,
        String paymentMethod,
        String counterpart,
        String notes,
        boolean linkedToAppointment,
        String appointmentId
) {}
