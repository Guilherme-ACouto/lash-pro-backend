package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.infrastructure.persistence.entity.InventoryMovementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryMovementJpaRepository extends JpaRepository<InventoryMovementEntity, UUID> {

    @Query("""
            SELECT m FROM InventoryMovementEntity m
            LEFT JOIN FETCH m.item i
            WHERE m.itemId = :itemId
            ORDER BY m.createdAt DESC
            """)
    Page<InventoryMovementEntity> findByItemIdOrderByCreatedAtDesc(
            @Param("itemId") UUID itemId,
            Pageable pageable
    );

    boolean existsByItemId(UUID itemId);
}
