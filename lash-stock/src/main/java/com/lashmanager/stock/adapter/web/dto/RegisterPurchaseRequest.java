package com.lashmanager.stock.adapter.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterPurchaseRequest(
        @NotNull @DecimalMin("0.001") BigDecimal quantity,
        @NotNull @DecimalMin("0.00") BigDecimal unitCost,
        String supplier,
        @NotNull LocalDate purchaseDate,
        @NotBlank String paymentType,
        LocalDate dueDate,
        String notes) {}
