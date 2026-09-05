package com.lashmanager.stock.infrastructure.persistence.mapper;

import com.lashmanager.stock.domain.model.InventoryItem;
import com.lashmanager.stock.infrastructure.persistence.entity.InventoryItemEntity;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemMapper {

  public InventoryItem toDomain(InventoryItemEntity e) {
    return InventoryItem.builder()
        .id(e.getId())
        .name(e.getName())
        .internalCode(e.getInternalCode())
        .unit(e.getUnit())
        .costPrice(e.getCostPrice())
        .supplier(e.getSupplier())
        .currentQuantity(e.getCurrentQuantity())
        .minimumQuantity(e.getMinimumQuantity())
        .active(e.isActive())
        .notes(e.getNotes())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt())
        .build();
  }

  public InventoryItemEntity toEntity(InventoryItem item) {
    return InventoryItemEntity.builder()
        .id(item.getId())
        .name(item.getName())
        .internalCode(item.getInternalCode())
        .unit(item.getUnit())
        .costPrice(item.getCostPrice())
        .supplier(item.getSupplier())
        .currentQuantity(item.getCurrentQuantity())
        .minimumQuantity(item.getMinimumQuantity())
        .active(item.isActive())
        .notes(item.getNotes())
        .createdAt(item.getCreatedAt())
        .updatedAt(item.getUpdatedAt())
        .build();
  }
}
