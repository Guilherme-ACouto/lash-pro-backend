package com.lashmanager.services.application.service;

import com.lashmanager.services.application.command.CreateServiceCommand;
import com.lashmanager.services.domain.port.in.CreateServiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateServiceApplicationService {

  private final CreateServiceUseCase createServiceUseCase;

  public CreateServiceUseCase.ServiceResult when(CreateServiceCommand command) {
    return createServiceUseCase.execute(command.toDomainCommand());
  }
}
