package com.lashmanager.clients.application.service;

import com.lashmanager.clients.application.command.UpdateClientCommand;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import com.lashmanager.clients.domain.port.in.UpdateClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateClientApplicationService {

  private final UpdateClientUseCase updateClientUseCase;

  public CreateClientUseCase.ClientResult when(UpdateClientCommand command) {
    return updateClientUseCase.execute(command.getId(), command.toDomainCommand());
  }
}
