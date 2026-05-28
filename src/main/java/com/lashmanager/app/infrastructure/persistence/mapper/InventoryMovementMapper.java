package com.lashmanager.app.infrastructure.persistence.mapper;

import com.lashmanager.app.domain.model.InventoryMovement;
import com.lashmanager.app.domain.model.MovementReason;
import com.lashmanager.app.domain.model.MovementType;
import com.lashmanager.app.domain.model.PurchasePaymentType;
import com.lashmanager.app.infrastructure.persistence.entity.InventoryMovementEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InventoryMovementMapper {

    public InventoryMovement toDomain(InventoryMovementEntity entity) {
        if (entity == null) return null;
        BigDecimal totalCost = null;
        if (entity.getUnitCost() != null && entity.getQuantity() != null) {
            totalCost = entity.getQuantity().multiply(entity.getUnitCost());
        }
        String itemName = entity.getItem() != null ? entity.getItem().getName() : null;
        return InventoryMovement.builder()
                .id(entity.getId())
                .itemId(entity.getItemId())
                .itemName(itemName)
                .type(entity.getType() != null ? MovementType.valueOf(entity.getType()) : null)
                .reason(entity.getReason() != null ? MovementReason.valueOf(entity.getReason()) : null)
                .quantity(entity.getQuantity())
                .unitCost(entity.getUnitCost())
                .totalCost(totalCost)
                .supplier(entity.getSupplier())
                .purchaseDate(entity.getPurchaseDate())
                .paymentType(entity.getPaymentType() != null ? PurchasePaymentType.valueOf(entity.getPaymentType()) : null)
                .dueDate(entity.getDueDate())
                .financialEntryId(entity.getFinancialEntryId())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public InventoryMovementEntity toEntity(InventoryMovement domain) {
        if (domain == null) return null;
        return InventoryMovementEntity.builder()
                .id(domain.getId())
                .itemId(domain.getItemId())
                .type(domain.getType() != null ? domain.getType().name() : null)
                .reason(domain.getReason() != null ? domain.getReason().name() : null)
                .quantity(domain.getQuantity())
                .unitCost(domain.getUnitCost())
                .supplier(domain.getSupplier())
                .purchaseDate(domain.getPurchaseDate())
                .paymentType(domain.getPaymentType() != null ? domain.getPaymentType().name() : null)
                .dueDate(domain.getDueDate())
                .financialEntryId(domain.getFinancialEntryId())
                .notes(domain.getNotes())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
