package com.lashmanager.stock.domain.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface RegisterManualExitUseCase {

  record RegisterManualExitCommand(
      UUID itemId, BigDecimal quantity, String reason, String notes, LocalDate exitDate) {}

  RegisterPurchaseUseCase.InventoryMovementResult execute(RegisterManualExitCommand command);
}
