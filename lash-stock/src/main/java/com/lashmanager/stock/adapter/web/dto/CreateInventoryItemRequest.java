package com.lashmanager.stock.adapter.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateInventoryItemRequest(
        @NotBlank String name,
        String internalCode,
        @NotBlank String unit,
        @NotNull @DecimalMin("0.00") BigDecimal costPrice,
        String supplier,
        @NotNull @DecimalMin("0") BigDecimal currentQuantity,
        @NotNull @DecimalMin("0") BigDecimal minimumQuantity,
        String notes) {}
