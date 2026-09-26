package com.bravapro.stock.infrastructure.persistence.repository;

import com.bravapro.stock.domain.model.InventoryMovement;
import com.bravapro.stock.domain.port.out.InventoryMovementRepository;
import com.bravapro.stock.infrastructure.persistence.mapper.InventoryMovementMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
