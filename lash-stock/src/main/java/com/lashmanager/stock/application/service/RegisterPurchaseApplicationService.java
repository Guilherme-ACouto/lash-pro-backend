package com.lashmanager.stock.application.service;

import com.lashmanager.stock.application.command.RegisterPurchaseCommand;
import com.lashmanager.stock.domain.port.in.RegisterPurchaseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterPurchaseApplicationService {

  private final RegisterPurchaseUseCase registerPurchaseUseCase;

  public RegisterPurchaseUseCase.RegisterPurchaseResult when(RegisterPurchaseCommand command) {
    return registerPurchaseUseCase.execute(command.toDomainCommand());
  }
}
