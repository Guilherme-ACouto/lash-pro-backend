package com.lashmanager.app.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateInventoryItemRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Unidade é obrigatória")
        String unit,

        @NotNull(message = "Preço de custo é obrigatório")
        @Positive(message = "Preço de custo deve ser maior que zero")
        BigDecimal costPrice,

        String supplier,

        @NotNull(message = "Quantidade mínima é obrigatória")
        @PositiveOrZero(message = "Quantidade mínima não pode ser negativa")
        BigDecimal minimumQuantity,

        String notes
) {}
