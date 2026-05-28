package com.lashmanager.app.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterPurchaseRequest(
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        BigDecimal quantity,

        @NotNull(message = "Preço unitário é obrigatório")
        @Positive(message = "Preço unitário deve ser maior que zero")
        BigDecimal unitCost,

        String supplier,

        @NotNull(message = "Data da compra é obrigatória")
        LocalDate purchaseDate,

        @NotBlank(message = "Forma de pagamento é obrigatória")
        String paymentType,

        LocalDate dueDate,

        String notes
) {}
