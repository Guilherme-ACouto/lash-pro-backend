package com.lashmanager.app.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterManualExitRequest(
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        BigDecimal quantity,

        @NotBlank(message = "Motivo é obrigatório")
        String reason,

        String notes,

        @NotNull(message = "Data é obrigatória")
        LocalDate exitDate
) {}
