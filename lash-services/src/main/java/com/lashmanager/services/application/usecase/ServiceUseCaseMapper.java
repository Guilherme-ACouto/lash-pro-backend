package com.lashmanager.services.application.usecase;

import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.domain.port.in.CreateServiceUseCase;

public final class ServiceUseCaseMapper {

  private ServiceUseCaseMapper() {}

  public static CreateServiceUseCase.ServiceResult toResult(ServiceOffering service) {
    return new CreateServiceUseCase.ServiceResult(
        service.getId(),
        service.getName(),
        service.getDescription(),
        service.getPrice(),
        service.getDurationMinutes(),
        service.isActive(),
        service.getCreatedAt() != null ? service.getCreatedAt().toString() : null);
  }
}
