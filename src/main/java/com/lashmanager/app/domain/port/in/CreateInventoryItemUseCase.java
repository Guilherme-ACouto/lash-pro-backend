package com.lashmanager.app.domain.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface CreateInventoryItemUseCase {

    record CreateInventoryItemCommand(
            String name,
            String internalCode,
            String unit,
            BigDecimal costPrice,
            String supplier,
            BigDecimal currentQuantity,
            BigDecimal minimumQuantity,
            String notes
    ) {}

    record InventoryItemResult(
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

    InventoryItemResult execute(CreateInventoryItemCommand command);
}
