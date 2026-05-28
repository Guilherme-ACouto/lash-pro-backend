package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.InventoryItem;
import com.lashmanager.app.domain.model.InventoryMovement;
import com.lashmanager.app.domain.port.in.CreateInventoryItemUseCase.InventoryItemResult;
import com.lashmanager.app.domain.port.in.RegisterPurchaseUseCase.InventoryMovementResult;

public final class InventoryUseCaseMapper {

    private InventoryUseCaseMapper() {}

    public static InventoryItemResult toItemResult(InventoryItem item) {
        return new InventoryItemResult(
                item.getId(),
                item.getName(),
                item.getInternalCode(),
                item.getUnit(),
                item.getCostPrice(),
                item.getSupplier(),
                item.getCurrentQuantity(),
                item.getMinimumQuantity(),
                item.isActive(),
                item.isBelowMinimum(),
                item.isOutOfStock(),
                item.getNotes(),
                item.getCreatedAt() != null ? item.getCreatedAt().toString() : null
        );
    }

    public static InventoryMovementResult toMovementResult(InventoryMovement m) {
        return new InventoryMovementResult(
                m.getId(),
                m.getItemId(),
                m.getItemName(),
                m.getType() != null ? m.getType().name() : null,
                m.getReason() != null ? m.getReason().name() : null,
                m.getQuantity(),
                m.getUnitCost(),
                m.getTotalCost(),
                m.getSupplier(),
                m.getPurchaseDate() != null ? m.getPurchaseDate().toString() : null,
                m.getPaymentType() != null ? m.getPaymentType().name() : null,
                m.getDueDate() != null ? m.getDueDate().toString() : null,
                m.getFinancialEntryId() != null ? m.getFinancialEntryId().toString() : null,
                m.getNotes(),
                m.getCreatedAt() != null ? m.getCreatedAt().toString() : null
        );
    }
}
