package com.lashmanager.app.adapter.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record InventoryItemResponse(
        UUID id,
        String name,
        String internalCode,
        String unit,
        BigDecimal costPrice,
        String supplier,
        BigDecimal currentQuantity,
        BigDecimal minimumQuantity,
        boolean active,
        boolean belowMinimum,
        boolean outOfStock,
        String notes,
        String createdAt
) {}
