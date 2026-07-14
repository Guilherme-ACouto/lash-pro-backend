package com.lashmanager.stock.application.usecase;

import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.stock.domain.model.InventoryItem;
import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.in.DeactivateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class DeactivateInventoryItemUseCaseImpl implements DeactivateInventoryItemUseCase {

    private final InventoryItemRepository itemRepository;

    @Override
    public CreateInventoryItemUseCase.InventoryItemResult execute(UUID id, boolean activate) {
        InventoryItem item = itemRepository.findById(id)
                .orElseThrow(() -> new InventoryItemNotFoundException(id));

        InventoryItem updated = item.toBuilder()
                .active(activate)
                .updatedAt(LocalDateTime.now())
                .build();

        return InventoryUseCaseMapper.toItemResult(itemRepository.save(updated));
    }
}
