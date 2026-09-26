package com.bravapro.stock.adapter.web.dto;

import com.bravapro.stock.domain.model.InventoryItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        String notes,
        boolean belowMinimum,
        boolean outOfStock,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public static InventoryItemResponse from(InventoryItem item) {
        return new InventoryItemResponse(
                item.getId(),
                item.getName(),
                item.getInternalCode(),
                item.getUnit(),
                item.getCostPrice(),
                item.getSupplier(),
                item.getCurrentQuantity(),
                item.getMinimumQuantity(),
                item.isActive(),
                item.getNotes(),
                item.isBelowMinimum(),
                item.isOutOfStock(),
                item.getCreatedAt(),
                item.getUpdatedAt());
    }
}
