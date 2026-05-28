package com.lashmanager.app.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inventory_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "internal_code", unique = true)
    private String internalCode;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "cost_price", nullable = false)
    private BigDecimal costPrice;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "current_quantity", nullable = false)
    private BigDecimal currentQuantity;

    @Column(name = "minimum_quantity", nullable = false)
    private BigDecimal minimumQuantity;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
        if (active == false) active = true;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
