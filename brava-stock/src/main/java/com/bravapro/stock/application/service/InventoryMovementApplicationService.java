package com.bravapro.stock.application.service;

import com.bravapro.stock.application.command.RegisterManualExitCommand;
import com.bravapro.stock.application.command.RegisterPurchaseCommand;
import com.bravapro.stock.domain.exception.InventoryItemNotFoundException;
import com.bravapro.stock.domain.model.InventoryItem;
import com.bravapro.stock.domain.model.InventoryMovement;
import com.bravapro.stock.domain.port.in.InventoryMovementUseCase;
import com.bravapro.stock.domain.port.out.InventoryItemRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryMovementApplicationService {

    private final InventoryMovementUseCase inventoryMovementUseCase;
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryMovement when(RegisterPurchaseCommand command) {
        return inventoryMovementUseCase.registerPurchase(getOneItem(command.getItemId()), command);
    }

    public InventoryMovement when(RegisterManualExitCommand command) {
        return inventoryMovementUseCase.registerManualExit(getOneItem(command.getItemId()), command);
    }

    private InventoryItem getOneItem(UUID id) {
        return inventoryItemRepository.findById(id).orElseThrow(() -> new InventoryItemNotFoundException(id));
    }
}
