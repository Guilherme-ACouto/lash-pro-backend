package com.lashmanager.stock.domain.port.out;

import com.lashmanager.stock.domain.model.InventoryItem;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Porta de leitura — separada de InventoryItemRepository (escrita) conforme RBK-27. Reaproveita o
 * mesmo InventoryItemJpaRepository/Mapper por baixo.
 */
public interface InventoryItemQueryRepository {
  Optional<InventoryItem> findById(UUID id);

  Page<InventoryItem> listWithFilters(
      String search, Boolean active, boolean onlyLowStock, Pageable pageable);
}
