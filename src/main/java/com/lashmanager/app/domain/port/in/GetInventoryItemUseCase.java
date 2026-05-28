package com.lashmanager.app.domain.port.in;

import com.lashmanager.app.domain.port.in.CreateInventoryItemUseCase.InventoryItemResult;

import java.util.UUID;

public interface GetInventoryItemUseCase {
    InventoryItemResult execute(UUID id);
}
