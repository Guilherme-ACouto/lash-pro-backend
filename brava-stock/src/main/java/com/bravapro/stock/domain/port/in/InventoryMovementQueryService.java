package com.bravapro.stock.domain.port.in;

import com.bravapro.stock.domain.model.InventoryMovement;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryMovementQueryService {

    Page<InventoryMovement> listByItemId(UUID itemId, Pageable pageable);
}
