package com.lashmanager.services.domain.port.in;

import java.util.UUID;

public interface GetServiceUseCase {
  CreateServiceUseCase.ServiceResult execute(UUID id);
}
