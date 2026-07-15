package com.lashmanager.stock.infrastructure.persistence.repository;

import com.lashmanager.stock.domain.model.InventoryMovement;
import com.lashmanager.stock.domain.port.out.InventoryMovementRepository;
import com.lashmanager.stock.infrastructure.persistence.mapper.InventoryMovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class InventoryMovementRepositoryImpl implements InventoryMovementRepository {

    private final InventoryMovementJpaRepository jpaRepository;
    private final InventoryMovementMapper mapper;

    @Override
    public InventoryMovement save(InventoryMovement movement) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(movement)));
    }

    @Override
    public boolean existsByItemId(UUID itemId) {
        return jpaRepository.existsByItemId(itemId);
    }
}
