package com.lashmanager.app.domain.port.in;

import com.lashmanager.app.domain.port.in.CreateInventoryItemUseCase.InventoryItemResult;

import java.math.BigDecimal;
import java.util.UUID;

public interface UpdateInventoryItemUseCase {

    record UpdateInventoryItemCommand(
            UUID id,
            String name,
            String unit,
            BigDecimal costPrice,
            String supplier,
            BigDecimal minimumQuantity,
            String notes
    ) {}

    InventoryItemResult execute(UpdateInventoryItemCommand command);
}
