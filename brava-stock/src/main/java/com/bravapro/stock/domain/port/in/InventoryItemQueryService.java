package com.bravapro.stock.domain.port.in;

import com.bravapro.stock.domain.model.InventoryItem;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryItemQueryService {

    InventoryItem getById(UUID id);

    Page<InventoryItem> list(String search, String status, String filter, Pageable pageable);
}
