package com.bravapro.stock.infrastructure.persistence.repository;

import com.bravapro.stock.domain.model.InventoryItem;
import com.bravapro.stock.domain.port.out.InventoryItemQueryRepository;
import com.bravapro.stock.infrastructure.persistence.mapper.InventoryItemMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InventoryItemQueryRepositoryImpl implements InventoryItemQueryRepository {

    private final InventoryItemJpaRepository jpaRepository;
    private final InventoryItemMapper mapper;

    @Override
    public Optional<InventoryItem> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<InventoryItem> listWithFilters(String search, Boolean active, boolean onlyLowStock, Pageable pageable) {
        return jpaRepository
                .findAllFiltered(search, active, onlyLowStock, pageable)
                .map(mapper::toDomain);
    }
}
