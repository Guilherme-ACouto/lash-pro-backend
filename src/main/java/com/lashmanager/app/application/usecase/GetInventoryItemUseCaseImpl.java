package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.app.domain.port.in.CreateInventoryItemUseCase.InventoryItemResult;
import com.lashmanager.app.domain.port.in.GetInventoryItemUseCase;
import com.lashmanager.app.domain.port.out.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetInventoryItemUseCaseImpl implements GetInventoryItemUseCase {

    private final InventoryItemRepository itemRepository;

    @Override
    public InventoryItemResult execute(UUID id) {
        return itemRepository.findById(id)
                .map(InventoryUseCaseMapper::toItemResult)
                .orElseThrow(() -> new InventoryItemNotFoundException(id));
    }
}
