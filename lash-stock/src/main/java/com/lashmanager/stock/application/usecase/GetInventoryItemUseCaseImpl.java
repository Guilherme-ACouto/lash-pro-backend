package com.lashmanager.stock.application.usecase;

import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.in.GetInventoryItemUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetInventoryItemUseCaseImpl implements GetInventoryItemUseCase {

    private final InventoryItemRepository itemRepository;

    @Override
    public CreateInventoryItemUseCase.InventoryItemResult execute(UUID id) {
        return itemRepository.findById(id)
                .map(InventoryUseCaseMapper::toItemResult)
                .orElseThrow(() -> new InventoryItemNotFoundException(id));
    }
}
