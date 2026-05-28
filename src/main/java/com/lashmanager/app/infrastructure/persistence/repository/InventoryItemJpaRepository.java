package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.infrastructure.persistence.entity.InventoryItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryItemJpaRepository extends JpaRepository<InventoryItemEntity, UUID> {

    boolean existsByInternalCode(String internalCode);

    boolean existsByInternalCodeAndIdNot(String internalCode, UUID id);

    @Query("""
            SELECT i FROM InventoryItemEntity i
            WHERE (:active IS NULL OR i.active = :active)
            AND (LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%'))
                 OR LOWER(COALESCE(i.internalCode, '')) LIKE LOWER(CONCAT('%', :search, '%')))
            AND (:onlyLowStock = false OR i.currentQuantity <= i.minimumQuantity)
            ORDER BY i.name ASC
            """)
    Page<InventoryItemEntity> findWithFilters(
            @Param("search") String search,
            @Param("active") Boolean active,
            @Param("onlyLowStock") boolean onlyLowStock,
            Pageable pageable
    );
}
