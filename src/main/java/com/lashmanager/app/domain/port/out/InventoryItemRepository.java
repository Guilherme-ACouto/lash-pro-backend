package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface InventoryItemRepository {
    InventoryItem save(InventoryItem item);
    Optional<InventoryItem> findById(UUID id);
    Page<InventoryItem> listWithFilters(String search, Boolean active, boolean onlyLowStock, Pageable pageable);
    boolean existsByInternalCode(String code);
    boolean existsByInternalCodeAndIdNot(String code, UUID id);
    void delete(UUID id);
}
