package com.lashmanager.app.infrastructure.persistence.mapper;

import com.lashmanager.app.domain.model.InventoryItem;
import com.lashmanager.app.infrastructure.persistence.entity.InventoryItemEntity;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemMapper {

    public InventoryItem toDomain(InventoryItemEntity entity) {
        if (entity == null) return null;
        return InventoryItem.builder()
                .id(entity.getId())
                .name(entity.getName())
                .internalCode(entity.getInternalCode())
                .unit(entity.getUnit())
                .costPrice(entity.getCostPrice())
                .supplier(entity.getSupplier())
                .currentQuantity(entity.getCurrentQuantity())
                .minimumQuantity(entity.getMinimumQuantity())
                .active(entity.isActive())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public InventoryItemEntity toEntity(InventoryItem domain) {
        if (domain == null) return null;
        return InventoryItemEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .internalCode(domain.getInternalCode())
                .unit(domain.getUnit())
                .costPrice(domain.getCostPrice())
                .supplier(domain.getSupplier())
                .currentQuantity(domain.getCurrentQuantity())
                .minimumQuantity(domain.getMinimumQuantity())
                .active(domain.isActive())
                .notes(domain.getNotes())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
