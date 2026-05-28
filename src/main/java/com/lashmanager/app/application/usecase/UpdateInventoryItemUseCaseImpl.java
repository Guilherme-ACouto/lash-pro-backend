package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.InventoryItemCodeAlreadyExistsException;
import com.lashmanager.app.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.app.domain.model.InventoryItem;
import com.lashmanager.app.domain.port.in.CreateInventoryItemUseCase.InventoryItemResult;
import com.lashmanager.app.domain.port.in.UpdateInventoryItemUseCase;
import com.lashmanager.app.domain.port.out.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateInventoryItemUseCaseImpl implements UpdateInventoryItemUseCase {

    private final InventoryItemRepository itemRepository;

    @Override
    public InventoryItemResult execute(UpdateInventoryItemCommand command) {
        InventoryItem existing = itemRepository.findById(command.id())
                .orElseThrow(() -> new InventoryItemNotFoundException(command.id()));

        InventoryItem updated = InventoryItem.builder()
                .id(existing.getId())
                .name(command.name())
                .internalCode(existing.getInternalCode())
                .unit(command.unit())
                .costPrice(command.costPrice())
                .supplier(command.supplier())
                .currentQuantity(existing.getCurrentQuantity())
                .minimumQuantity(command.minimumQuantity())
                .active(existing.isActive())
                .notes(command.notes())
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        return InventoryUseCaseMapper.toItemResult(itemRepository.save(updated));
    }
}
