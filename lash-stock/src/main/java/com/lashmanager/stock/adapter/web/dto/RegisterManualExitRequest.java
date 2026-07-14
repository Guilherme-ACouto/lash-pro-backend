package com.lashmanager.stock.adapter.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterManualExitRequest(
        @NotNull @DecimalMin("0.001") BigDecimal quantity,
        @NotBlank String reason,
        @NotNull LocalDate exitDate,
        String notes
) {}
