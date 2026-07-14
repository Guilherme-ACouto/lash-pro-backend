package com.lashmanager.stock.application.usecase;

import com.lashmanager.stock.domain.exception.InventoryItemHasMovementsException;
import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.stock.domain.port.in.DeleteInventoryItemUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;
import com.lashmanager.stock.domain.port.out.InventoryMovementRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteInventoryItemUseCaseImpl implements DeleteInventoryItemUseCase {

    private final InventoryItemRepository itemRepository;
    private final InventoryMovementRepository movementRepository;

    @Override
    public void execute(UUID id) {
        if (itemRepository.findById(id).isEmpty()) {
            throw new InventoryItemNotFoundException(id);
        }
        if (movementRepository.existsByItemId(id)) {
            throw new InventoryItemHasMovementsException();
        }
        itemRepository.delete(id);
    }
}
