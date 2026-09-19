package com.lashmanager.stock.application.service;

import com.lashmanager.stock.application.command.CreateInventoryItemCommand;
import com.lashmanager.stock.application.command.DeactivateInventoryItemCommand;
import com.lashmanager.stock.application.command.DeleteInventoryItemCommand;
import com.lashmanager.stock.application.command.ReactivateInventoryItemCommand;
import com.lashmanager.stock.application.command.UpdateInventoryItemCommand;
import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.stock.domain.model.InventoryItem;
import com.lashmanager.stock.domain.port.in.InventoryItemUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryItemApplicationService {

    private final InventoryItemUseCase inventoryItemUseCase;
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItem when(CreateInventoryItemCommand command) {
        return inventoryItemUseCase.create(command);
    }

    public void when(UpdateInventoryItemCommand command) {
        inventoryItemUseCase.update(getOne(command.getId()), command);
    }

    public void when(DeleteInventoryItemCommand command) {
        inventoryItemUseCase.delete(getOne(command.getId()));
    }

    public void when(DeactivateInventoryItemCommand command) {
        inventoryItemUseCase.deactivate(getOne(command.getId()));
    }

    public void when(ReactivateInventoryItemCommand command) {
        inventoryItemUseCase.reactivate(getOne(command.getId()));
    }

    private InventoryItem getOne(UUID id) {
        return inventoryItemRepository.findById(id).orElseThrow(() -> new InventoryItemNotFoundException(id));
    }
}
