package com.lashmanager.services.domain.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface UpdateServiceUseCase {

    record UpdateServiceCommand(
            String name,
            String description,
            BigDecimal price,
            int durationMinutes
    ) {}

    CreateServiceUseCase.ServiceResult execute(UUID id, UpdateServiceCommand command);
}
