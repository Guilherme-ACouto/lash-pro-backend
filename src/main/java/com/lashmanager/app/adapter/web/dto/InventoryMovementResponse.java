package com.lashmanager.app.adapter.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record InventoryMovementResponse(
        UUID id,
        UUID itemId,
        String itemName,
        String type,
        String reason,
        BigDecimal quantity,
        BigDecimal unitCost,
        BigDecimal totalCost,
        String supplier,
        String purchaseDate,
        String paymentType,
        String dueDate,
        String financialEntryId,
        String notes,
        String createdAt
) {}
