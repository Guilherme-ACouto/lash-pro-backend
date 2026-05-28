package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.InventoryItemHasMovementsException;
import com.lashmanager.app.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.app.domain.port.in.DeleteInventoryItemUseCase;
import com.lashmanager.app.domain.port.out.InventoryItemRepository;
import com.lashmanager.app.domain.port.out.InventoryMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteInventoryItemUseCaseImpl implements DeleteInventoryItemUseCase {

    private final InventoryItemRepository itemRepository;
    private final InventoryMovementRepository movementRepository;

    @Override
    public void execute(UUID id) {
        itemRepository.findById(id)
                .orElseThrow(() -> new InventoryItemNotFoundException(id));

        if (movementRepository.existsByItemId(id)) {
            throw new InventoryItemHasMovementsException(id);
        }

        itemRepository.delete(id);
    }
}
