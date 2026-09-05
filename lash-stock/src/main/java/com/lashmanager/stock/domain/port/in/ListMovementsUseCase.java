package com.lashmanager.stock.domain.port.in;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListMovementsUseCase {
    Page<RegisterPurchaseUseCase.InventoryMovementResult> execute(UUID itemId, Pageable pageable);
}
