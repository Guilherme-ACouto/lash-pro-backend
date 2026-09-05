package com.lashmanager.services.domain.model;

import com.lashmanager.core.domain.model.DomainEntity;
import com.lashmanager.services.application.command.UpdateServiceCommand;

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
public class ServiceOffering implements DomainEntity {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int durationMinutes;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(UpdateServiceCommand command) {
        this.name = command.getName();
        this.description = command.getDescription();
        this.price = command.getPrice();
        this.durationMinutes = command.getDurationMinutes();
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
}
