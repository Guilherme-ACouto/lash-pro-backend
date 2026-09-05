package com.lashmanager.stock.domain.port.out;

import com.lashmanager.stock.domain.model.InventoryItem;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de escrita. Leitura (listagem/filtro) foi separada para InventoryItemQueryRepository
 * (RBK-27) — findById continua aqui porque os use cases de escrita precisam do agregado completo.
 */
public interface InventoryItemRepository {
    InventoryItem save(InventoryItem item);

    Optional<InventoryItem> findById(UUID id);

    boolean existsByInternalCode(String code);

    boolean existsByInternalCodeAndIdNot(String code, UUID id);

    void delete(UUID id);
}
