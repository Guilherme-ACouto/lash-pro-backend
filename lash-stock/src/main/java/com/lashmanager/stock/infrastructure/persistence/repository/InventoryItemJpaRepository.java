package com.lashmanager.stock.infrastructure.persistence.repository;

import com.lashmanager.stock.infrastructure.persistence.entity.InventoryItemEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryItemJpaRepository extends JpaRepository<InventoryItemEntity, UUID> {

  boolean existsByInternalCode(String internalCode);

  boolean existsByInternalCodeAndIdNot(String internalCode, UUID id);

  @Query(
      """
        SELECT i FROM InventoryItemEntity i
        WHERE (:search = '' OR LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%'))
                            OR LOWER(i.internalCode) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:active IS NULL OR i.active = :active)
          AND (:onlyLowStock = false OR i.currentQuantity < i.minimumQuantity)
        ORDER BY i.name
    """)
  Page<InventoryItemEntity> findAllFiltered(
      @Param("search") String search,
      @Param("active") Boolean active,
      @Param("onlyLowStock") boolean onlyLowStock,
      Pageable pageable);
}
