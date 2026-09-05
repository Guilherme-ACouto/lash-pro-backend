package com.lashmanager.services.application.service;

import com.lashmanager.services.application.command.DeleteServiceCommand;
import com.lashmanager.services.domain.port.in.DeleteServiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteServiceApplicationService {

  private final DeleteServiceUseCase deleteServiceUseCase;

  public void when(DeleteServiceCommand command) {
    deleteServiceUseCase.execute(command.getId());
  }
}
