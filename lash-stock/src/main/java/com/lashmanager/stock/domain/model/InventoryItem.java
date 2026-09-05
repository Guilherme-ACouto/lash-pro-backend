package com.lashmanager.stock.domain.model;

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
public class InventoryItem {
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
}
