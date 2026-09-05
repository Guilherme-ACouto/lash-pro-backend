package com.lashmanager.stock.application.service;

import com.lashmanager.stock.application.command.RegisterManualExitCommand;
import com.lashmanager.stock.domain.port.in.RegisterManualExitUseCase;
import com.lashmanager.stock.domain.port.in.RegisterPurchaseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterManualExitApplicationService {

  private final RegisterManualExitUseCase registerManualExitUseCase;

  public RegisterPurchaseUseCase.InventoryMovementResult when(RegisterManualExitCommand command) {
    return registerManualExitUseCase.execute(command.toDomainCommand());
  }
}
