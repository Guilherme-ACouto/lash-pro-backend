package com.lashmanager.stock.domain.port.out;

import com.lashmanager.stock.domain.model.InventoryMovement;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Porta de leitura — separada de InventoryMovementRepository (escrita) conforme RBK-27. Movimentos
 * são append-only (só `save`); listagem é o único caso de leitura, daí a porta ficar bem enxuta.
 */
public interface InventoryMovementQueryRepository {
  Page<InventoryMovement> findByItemId(UUID itemId, Pageable pageable);
}
