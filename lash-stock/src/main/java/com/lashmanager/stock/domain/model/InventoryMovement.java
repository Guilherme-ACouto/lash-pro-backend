package com.lashmanager.stock.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryMovement {
    private UUID id;
    private UUID itemId;
    private String itemName;
    private MovementType type;
    private MovementReason reason;
    private BigDecimal quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private String supplier;
    private LocalDate purchaseDate;
    private PurchasePaymentType paymentType;
    private LocalDate dueDate;
    private UUID financialEntryId;
    private String notes;
    private LocalDateTime createdAt;
}
