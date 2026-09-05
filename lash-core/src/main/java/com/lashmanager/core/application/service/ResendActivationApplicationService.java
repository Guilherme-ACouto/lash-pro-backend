package com.lashmanager.core.application.service;

import com.lashmanager.core.application.command.ResendActivationCommand;
import com.lashmanager.core.domain.port.in.ResendActivationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResendActivationApplicationService {

  private final ResendActivationUseCase resendActivationUseCase;

  public void when(ResendActivationCommand command) {
    resendActivationUseCase.execute(command.getEmail());
  }
}
