package com.lashmanager.stock.domain.port.in;

import com.lashmanager.stock.domain.model.InventoryMovement;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryMovementQueryService {

    Page<InventoryMovement> listByItemId(UUID itemId, Pageable pageable);
}
