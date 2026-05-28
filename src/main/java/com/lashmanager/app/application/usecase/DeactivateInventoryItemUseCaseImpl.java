package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.app.domain.model.InventoryItem;
import com.lashmanager.app.domain.port.in.CreateInventoryItemUseCase.InventoryItemResult;
import com.lashmanager.app.domain.port.in.DeactivateInventoryItemUseCase;
import com.lashmanager.app.domain.port.out.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeactivateInventoryItemUseCaseImpl implements DeactivateInventoryItemUseCase {

    private final InventoryItemRepository itemRepository;

    @Override
    public InventoryItemResult execute(UUID id, boolean activate) {
        InventoryItem existing = itemRepository.findById(id)
                .orElseThrow(() -> new InventoryItemNotFoundException(id));

        InventoryItem updated = InventoryItem.builder()
                .id(existing.getId())
                .name(existing.getName())
                .internalCode(existing.getInternalCode())
                .unit(existing.getUnit())
                .costPrice(existing.getCostPrice())
                .supplier(existing.getSupplier())
                .currentQuantity(existing.getCurrentQuantity())
                .minimumQuantity(existing.getMinimumQuantity())
                .active(activate)
                .notes(existing.getNotes())
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        return InventoryUseCaseMapper.toItemResult(itemRepository.save(updated));
    }
}
