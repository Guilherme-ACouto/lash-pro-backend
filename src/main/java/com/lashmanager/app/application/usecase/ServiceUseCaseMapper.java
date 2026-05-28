package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.Service;
import com.lashmanager.app.domain.port.in.CreateServiceUseCase;

public final class ServiceUseCaseMapper {

    private ServiceUseCaseMapper() {}

    public static CreateServiceUseCase.ServiceResult toResult(Service service) {
        return new CreateServiceUseCase.ServiceResult(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getDurationMinutes(),
                service.isActive(),
                service.getCreatedAt() != null ? service.getCreatedAt().toString() : null
        );
    }
}
