package com.lashmanager.stock.infrastructure.persistence.repository;

import com.lashmanager.stock.domain.model.InventoryMovement;
import com.lashmanager.stock.domain.port.out.InventoryMovementQueryRepository;
import com.lashmanager.stock.infrastructure.persistence.mapper.InventoryMovementMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InventoryMovementQueryRepositoryImpl implements InventoryMovementQueryRepository {

  private final InventoryMovementJpaRepository jpaRepository;
  private final InventoryMovementMapper mapper;

  @Override
  public Page<InventoryMovement> findByItemId(UUID itemId, Pageable pageable) {
    return jpaRepository.findByItemId(itemId, pageable).map(mapper::toDomain);
  }
}
