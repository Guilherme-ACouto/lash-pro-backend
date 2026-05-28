package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.domain.model.InventoryItem;
import com.lashmanager.app.domain.port.out.InventoryItemRepository;
import com.lashmanager.app.infrastructure.persistence.mapper.InventoryItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class InventoryItemRepositoryImpl implements InventoryItemRepository {

    private final InventoryItemJpaRepository jpaRepository;
    private final InventoryItemMapper mapper;

    @Override
    public InventoryItem save(InventoryItem item) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(item)));
    }

    @Override
    public Optional<InventoryItem> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<InventoryItem> listWithFilters(String search, Boolean active, boolean onlyLowStock, Pageable pageable) {
        String safeSearch = search != null ? search : "";
        return jpaRepository.findWithFilters(safeSearch, active, onlyLowStock, pageable)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByInternalCode(String code) {
        return jpaRepository.existsByInternalCode(code);
    }

    @Override
    public boolean existsByInternalCodeAndIdNot(String code, UUID id) {
        return jpaRepository.existsByInternalCodeAndIdNot(code, id);
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}
