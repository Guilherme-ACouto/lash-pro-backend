package com.lashmanager.app.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateInventoryItemRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        String internalCode,

        @NotBlank(message = "Unidade é obrigatória")
        String unit,

        @NotNull(message = "Preço de custo é obrigatório")
        @jakarta.validation.constraints.Positive(message = "Preço de custo deve ser maior que zero")
        BigDecimal costPrice,

        String supplier,

        @NotNull(message = "Quantidade atual é obrigatória")
        @PositiveOrZero(message = "Quantidade não pode ser negativa")
        BigDecimal currentQuantity,

        @NotNull(message = "Quantidade mínima é obrigatória")
        @PositiveOrZero(message = "Quantidade mínima não pode ser negativa")
        BigDecimal minimumQuantity,

        String notes
) {}
