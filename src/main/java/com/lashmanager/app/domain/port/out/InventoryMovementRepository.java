package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.InventoryMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InventoryMovementRepository {
    InventoryMovement save(InventoryMovement movement);
    Page<InventoryMovement> findByItemId(UUID itemId, Pageable pageable);
    boolean existsByItemId(UUID itemId);
}
