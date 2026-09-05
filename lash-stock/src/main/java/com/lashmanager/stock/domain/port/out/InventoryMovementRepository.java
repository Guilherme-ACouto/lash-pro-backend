package com.lashmanager.stock.domain.port.out;

import com.lashmanager.stock.domain.model.InventoryMovement;
import java.util.UUID;

/**
 * Porta de escrita. Leitura (listagem por item) foi separada para InventoryMovementQueryRepository
 * (RBK-27).
 */
public interface InventoryMovementRepository {
    InventoryMovement save(InventoryMovement movement);

    boolean existsByItemId(UUID itemId);
}
