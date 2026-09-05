package com.lashmanager.stock.infrastructure.persistence.mapper;

import com.lashmanager.stock.domain.model.*;
import com.lashmanager.stock.infrastructure.persistence.entity.InventoryItemEntity;
import com.lashmanager.stock.infrastructure.persistence.entity.InventoryMovementEntity;
import com.lashmanager.stock.infrastructure.persistence.repository.InventoryItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryMovementMapper {

    private final InventoryItemJpaRepository itemJpaRepository;

    public InventoryMovement toDomain(InventoryMovementEntity e) {
        return InventoryMovement.builder()
                .id(e.getId())
                .itemId(e.getItem() != null ? e.getItem().getId() : null)
                .itemName(e.getItemName())
                .type(e.getType() != null ? MovementType.valueOf(e.getType()) : null)
                .reason(e.getReason() != null ? MovementReason.valueOf(e.getReason()) : null)
                .quantity(e.getQuantity())
                .unitCost(e.getUnitCost())
                .totalCost(e.getTotalCost())
                .supplier(e.getSupplier())
                .purchaseDate(e.getPurchaseDate())
                .paymentType(e.getPaymentType() != null ? PurchasePaymentType.valueOf(e.getPaymentType()) : null)
                .dueDate(e.getDueDate())
                .financialEntryId(e.getFinancialEntryId())
                .notes(e.getNotes())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public InventoryMovementEntity toEntity(InventoryMovement m) {
        InventoryItemEntity itemRef = itemJpaRepository.getReferenceById(m.getItemId());
        return InventoryMovementEntity.builder()
                .id(m.getId())
                .item(itemRef)
                .itemName(m.getItemName())
                .type(m.getType() != null ? m.getType().name() : null)
                .reason(m.getReason() != null ? m.getReason().name() : null)
                .quantity(m.getQuantity())
                .unitCost(m.getUnitCost())
                .totalCost(m.getTotalCost())
                .supplier(m.getSupplier())
                .purchaseDate(m.getPurchaseDate())
                .paymentType(m.getPaymentType() != null ? m.getPaymentType().name() : null)
                .dueDate(m.getDueDate())
                .financialEntryId(m.getFinancialEntryId())
                .notes(m.getNotes())
                .createdAt(m.getCreatedAt())
                .build();
    }
}
