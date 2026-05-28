package com.lashmanager.app.domain.port.in;

import java.util.UUID;

public interface DeleteInventoryItemUseCase {
    void execute(UUID id);
}
