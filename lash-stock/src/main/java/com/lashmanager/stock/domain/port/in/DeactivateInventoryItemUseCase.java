package com.lashmanager.stock.domain.port.in;

import java.util.UUID;

public interface DeactivateInventoryItemUseCase {
    CreateInventoryItemUseCase.InventoryItemResult execute(UUID id, boolean activate);
}
