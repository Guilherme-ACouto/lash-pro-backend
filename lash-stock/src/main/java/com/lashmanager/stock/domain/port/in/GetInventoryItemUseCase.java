package com.lashmanager.stock.domain.port.in;

import java.util.UUID;

public interface GetInventoryItemUseCase {
    CreateInventoryItemUseCase.InventoryItemResult execute(UUID id);
}
