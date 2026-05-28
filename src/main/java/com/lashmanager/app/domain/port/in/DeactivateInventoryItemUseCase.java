package com.lashmanager.app.domain.port.in;

import com.lashmanager.app.domain.port.in.CreateInventoryItemUseCase.InventoryItemResult;

import java.util.UUID;

public interface DeactivateInventoryItemUseCase {
    InventoryItemResult execute(UUID id, boolean activate);
}
