package com.lashmanager.services.adapter.web.dto;

import com.lashmanager.services.domain.port.in.CreateServiceUseCase;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        int durationMinutes,
        boolean active,
        String createdAt
) {
    public static ServiceResponse from(CreateServiceUseCase.ServiceResult result) {
        return new ServiceResponse(
                result.id(),
                result.name(),
                result.description(),
                result.price(),
                result.durationMinutes(),
                result.active(),
                result.createdAt()
        );
    }
}
