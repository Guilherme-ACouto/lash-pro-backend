package com.lashmanager.services.domain.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface CreateServiceUseCase {

    record CreateServiceCommand(
            String name,
            String description,
            BigDecimal price,
            int durationMinutes
    ) {}

    record ServiceResult(
            UUID id,
            String name,
            String description,
            BigDecimal price,
            int durationMinutes,
            boolean active,
            String createdAt
    ) {}

    ServiceResult execute(CreateServiceCommand command);
}
