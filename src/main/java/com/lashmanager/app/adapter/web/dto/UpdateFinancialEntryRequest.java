package com.lashmanager.app.adapter.web.dto;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateFinancialEntryRequest(
        String description,
        @DecimalMin("0.01") BigDecimal amount,
        LocalDate dueDate,
        LocalDate paymentDate,
        String category,
        String expenseType,
        String paymentMethod,
        String receivedFrom,
        String notes
) {}
