package com.lashmanager.stock.application.service;

import com.lashmanager.stock.application.command.RegisterManualExitCommand;
import com.lashmanager.stock.application.command.RegisterPurchaseCommand;
import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.stock.domain.model.InventoryItem;
import com.lashmanager.stock.domain.model.InventoryMovement;
import com.lashmanager.stock.domain.port.in.InventoryMovementUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;

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
