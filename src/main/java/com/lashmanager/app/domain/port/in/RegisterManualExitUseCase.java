package com.lashmanager.app.domain.port.in;

import com.lashmanager.app.domain.port.in.RegisterPurchaseUseCase.InventoryMovementResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface RegisterManualExitUseCase {

    record RegisterManualExitCommand(
            UUID itemId,
            BigDecimal quantity,
            String reason,
            String notes,
            LocalDate exitDate
    ) {}

    InventoryMovementResult execute(RegisterManualExitCommand command);
}
