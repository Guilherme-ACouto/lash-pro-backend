package com.bravapro.services.adapter.web.dto;

import com.bravapro.services.domain.model.ServiceOffering;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ServiceResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        int durationMinutes,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public static ServiceResponse from(ServiceOffering service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getDurationMinutes(),
                service.isActive(),
                service.getCreatedAt(),
                service.getUpdatedAt());
    }
}
