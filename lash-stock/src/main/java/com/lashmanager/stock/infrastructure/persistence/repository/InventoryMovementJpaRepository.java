package com.lashmanager.stock.infrastructure.persistence.repository;

import com.lashmanager.stock.infrastructure.persistence.entity.InventoryMovementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface InventoryMovementJpaRepository extends JpaRepository<InventoryMovementEntity, UUID> {

    boolean existsByItemId(UUID itemId);

    @Query("SELECT m FROM InventoryMovementEntity m LEFT JOIN FETCH m.item WHERE m.item.id = :itemId ORDER BY m.createdAt DESC")
    Page<InventoryMovementEntity> findByItemId(@Param("itemId") UUID itemId, Pageable pageable);
}
