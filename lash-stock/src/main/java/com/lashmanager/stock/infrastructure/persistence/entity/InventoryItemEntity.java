package com.lashmanager.stock.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "inventory_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItemEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(name = "internal_code", nullable = false, unique = true)
  private String internalCode;

  @Column(nullable = false)
  private String unit;

  @Column(name = "cost_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal costPrice;

  private String supplier;

  @Column(name = "current_quantity", nullable = false, precision = 10, scale = 3)
  private BigDecimal currentQuantity;

  @Column(name = "minimum_quantity", nullable = false, precision = 10, scale = 3)
  private BigDecimal minimumQuantity;

  @Column(nullable = false)
  private boolean active;

  @Column(columnDefinition = "TEXT")
  private String notes;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}
