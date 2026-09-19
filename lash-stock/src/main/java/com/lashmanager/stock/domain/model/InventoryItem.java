package com.lashmanager.stock.domain.model;

import com.lashmanager.core.domain.model.DomainEntity;
import com.lashmanager.stock.application.command.UpdateInventoryItemCommand;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem implements DomainEntity {
    private UUID id;
    private String name;
    private String internalCode;
    private String unit;
    private BigDecimal costPrice;
    private String supplier;
    private BigDecimal currentQuantity;
    private BigDecimal minimumQuantity;
    private boolean active;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isBelowMinimum() {
        return currentQuantity.compareTo(minimumQuantity) <= 0;
    }

    public boolean isOutOfStock() {
        return currentQuantity.compareTo(BigDecimal.ZERO) == 0;
    }

    public void update(UpdateInventoryItemCommand command) {
        this.name = command.getName();
        this.unit = command.getUnit();
        this.costPrice = command.getCostPrice();
        this.supplier = command.getSupplier();
        this.minimumQuantity = command.getMinimumQuantity();
        this.notes = command.getNotes();
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void reactivate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void registerPurchase(BigDecimal quantity, BigDecimal unitCost, String resolvedSupplier) {
        this.costPrice = unitCost;
        this.supplier = resolvedSupplier;
        this.currentQuantity = this.currentQuantity.add(quantity);
        this.updatedAt = LocalDateTime.now();
    }

    public void registerManualExit(BigDecimal quantity) {
        this.currentQuantity = this.currentQuantity.subtract(quantity);
        this.updatedAt = LocalDateTime.now();
    }
}
