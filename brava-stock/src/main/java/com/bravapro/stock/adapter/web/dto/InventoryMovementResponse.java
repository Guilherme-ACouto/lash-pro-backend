package com.bravapro.stock.adapter.web.dto;

import com.bravapro.stock.domain.model.InventoryMovement;
import com.bravapro.stock.domain.model.MovementReason;
import com.bravapro.stock.domain.model.MovementType;
import com.bravapro.stock.domain.model.PurchasePaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryMovementResponse(
        UUID id,
        UUID itemId,
        String itemName,
        MovementType type,
        MovementReason reason,
        BigDecimal quantity,
        BigDecimal unitCost,
        BigDecimal totalCost,
        String supplier,
        LocalDate purchaseDate,
        PurchasePaymentType paymentType,
        LocalDate dueDate,
        UUID financialEntryId,
        String notes,
        LocalDateTime createdAt) {

    public static InventoryMovementResponse from(InventoryMovement movement) {
        return new InventoryMovementResponse(
                movement.getId(),
                movement.getItemId(),
                movement.getItemName(),
                movement.getType(),
                movement.getReason(),
                movement.getQuantity(),
                movement.getUnitCost(),
                movement.getTotalCost(),
                movement.getSupplier(),
                movement.getPurchaseDate(),
                movement.getPaymentType(),
                movement.getDueDate(),
                movement.getFinancialEntryId(),
                movement.getNotes(),
                movement.getCreatedAt());
    }
}
