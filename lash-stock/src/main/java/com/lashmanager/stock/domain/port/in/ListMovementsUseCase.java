package com.lashmanager.stock.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ListMovementsUseCase {
    Page<RegisterPurchaseUseCase.InventoryMovementResult> execute(UUID itemId, Pageable pageable);
}
