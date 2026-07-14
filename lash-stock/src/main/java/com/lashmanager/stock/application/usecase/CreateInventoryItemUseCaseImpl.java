package com.lashmanager.stock.application.usecase;

import com.lashmanager.stock.domain.exception.InventoryItemCodeAlreadyExistsException;
import com.lashmanager.stock.domain.model.InventoryItem;
import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateInventoryItemUseCaseImpl implements CreateInventoryItemUseCase {

    private final InventoryItemRepository itemRepository;

    @Override
    public InventoryItemResult execute(CreateInventoryItemCommand command) {
        String code = resolveCode(command.internalCode());
        if (itemRepository.existsByInternalCode(code)) {
            throw new InventoryItemCodeAlreadyExistsException(code);
        }

        InventoryItem item = InventoryItem.builder()
                .id(UUID.randomUUID())
                .name(command.name())
                .internalCode(code)
                .unit(command.unit())
                .costPrice(command.costPrice())
                .supplier(command.supplier())
                .currentQuantity(command.currentQuantity())
                .minimumQuantity(command.minimumQuantity())
                .active(true)
                .notes(command.notes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return InventoryUseCaseMapper.toItemResult(itemRepository.save(item));
    }

    private String resolveCode(String requested) {
        if (requested != null && !requested.isBlank()) return requested.trim();
        String generated;
        do {
            generated = "INS-" + String.format("%06d",
                    Math.abs(UUID.randomUUID().getLeastSignificantBits() % 1_000_000));
        } while (itemRepository.existsByInternalCode(generated));
        return generated;
    }
}
