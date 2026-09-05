package com.lashmanager.services.application.service;

import com.lashmanager.services.application.command.UpdateServiceCommand;
import com.lashmanager.services.domain.port.in.CreateServiceUseCase;
import com.lashmanager.services.domain.port.in.UpdateServiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateServiceApplicationService {

  private final UpdateServiceUseCase updateServiceUseCase;

  public CreateServiceUseCase.ServiceResult when(UpdateServiceCommand command) {
    return updateServiceUseCase.execute(command.getId(), command.toDomainCommand());
  }
}
