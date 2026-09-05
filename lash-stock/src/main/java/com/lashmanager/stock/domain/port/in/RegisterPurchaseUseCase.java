package com.lashmanager.stock.domain.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface RegisterPurchaseUseCase {

    record RegisterPurchaseCommand(
            UUID itemId,
            BigDecimal quantity,
            BigDecimal unitCost,
            String supplier,
            LocalDate purchaseDate,
            String paymentType,
            LocalDate dueDate,
            String notes) {}

    record InventoryMovementResult(
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
            String createdAt) {}

    record RegisterPurchaseResult(
            CreateInventoryItemUseCase.InventoryItemResult item,
            InventoryMovementResult movement,
            String financialEntryId) {}

    RegisterPurchaseResult execute(RegisterPurchaseCommand command);
}
