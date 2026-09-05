package com.lashmanager.stock.application.usecase;

import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.stock.domain.model.InventoryItem;
import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.in.UpdateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateInventoryItemUseCaseImpl implements UpdateInventoryItemUseCase {

    private final InventoryItemRepository itemRepository;

    @Override
    public CreateInventoryItemUseCase.InventoryItemResult execute(UpdateInventoryItemCommand command) {
        InventoryItem existing = itemRepository
                .findById(command.id())
                .orElseThrow(() -> new InventoryItemNotFoundException(command.id()));

        InventoryItem updated = existing.toBuilder()
                .name(command.name())
                .unit(command.unit())
                .costPrice(command.costPrice())
                .supplier(command.supplier())
                .minimumQuantity(command.minimumQuantity())
                .notes(command.notes())
                .updatedAt(LocalDateTime.now())
                .build();

        return InventoryUseCaseMapper.toItemResult(itemRepository.save(updated));
    }
}
