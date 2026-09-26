package com.bravapro.stock.application.usecase;

import com.bravapro.stock.application.command.CreateInventoryItemCommand;
import com.bravapro.stock.application.command.UpdateInventoryItemCommand;
import com.bravapro.stock.domain.exception.InventoryItemCodeAlreadyExistsException;
import com.bravapro.stock.domain.exception.InventoryItemHasMovementsException;
import com.bravapro.stock.domain.model.InventoryItem;
import com.bravapro.stock.domain.port.in.InventoryItemUseCase;
import com.bravapro.stock.domain.port.out.InventoryItemRepository;
import com.bravapro.stock.domain.port.out.InventoryMovementRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryItemUseCaseImpl implements InventoryItemUseCase {

    private final InventoryItemRepository itemRepository;
    private final InventoryMovementRepository movementRepository;

    @Override
    public InventoryItem create(CreateInventoryItemCommand command) {
        String code = resolveCode(command.getInternalCode());
        if (itemRepository.existsByInternalCode(code)) {
            throw new InventoryItemCodeAlreadyExistsException(code);
        }

        InventoryItem item = InventoryItem.builder()
                .id(UUID.randomUUID())
                .name(command.getName())
                .internalCode(code)
                .unit(command.getUnit())
                .costPrice(command.getCostPrice())
                .supplier(command.getSupplier())
                .currentQuantity(command.getCurrentQuantity())
                .minimumQuantity(command.getMinimumQuantity())
                .active(true)
                .notes(command.getNotes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return itemRepository.save(item);
    }

    @Override
    public void update(InventoryItem item, UpdateInventoryItemCommand command) {
        item.update(command);
        itemRepository.save(item);
    }

    @Override
    public void delete(InventoryItem item) {
        if (movementRepository.existsByItemId(item.getId())) {
            throw new InventoryItemHasMovementsException();
        }
        itemRepository.delete(item.getId());
    }

    @Override
    public void deactivate(InventoryItem item) {
        item.deactivate();
        itemRepository.save(item);
    }

    @Override
    public void reactivate(InventoryItem item) {
        item.reactivate();
        itemRepository.save(item);
    }

    private String resolveCode(String requested) {
        if (requested != null && !requested.isBlank()) {
            return requested.trim();
        }
        String generated;
        do {
            generated =
                    "INS-" + String.format("%06d", Math.abs(UUID.randomUUID().getLeastSignificantBits() % 1_000_000));
        } while (itemRepository.existsByInternalCode(generated));
        return generated;
    }
}
