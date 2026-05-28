package com.lashmanager.app.domain.port.in;

import com.lashmanager.app.domain.port.in.RegisterPurchaseUseCase.InventoryMovementResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ListMovementsUseCase {
    Page<InventoryMovementResult> execute(UUID itemId, Pageable pageable);
}
