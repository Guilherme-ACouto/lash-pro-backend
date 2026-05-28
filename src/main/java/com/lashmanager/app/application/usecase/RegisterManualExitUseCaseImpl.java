package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.app.domain.model.InventoryItem;
import com.lashmanager.app.domain.model.InventoryMovement;
import com.lashmanager.app.domain.model.MovementReason;
import com.lashmanager.app.domain.model.MovementType;
import com.lashmanager.app.domain.port.in.RegisterManualExitUseCase;
import com.lashmanager.app.domain.port.in.RegisterPurchaseUseCase.InventoryMovementResult;
import com.lashmanager.app.domain.port.out.InventoryItemRepository;
import com.lashmanager.app.domain.port.out.InventoryMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterManualExitUseCaseImpl implements RegisterManualExitUseCase {

    private final InventoryItemRepository itemRepository;
    private final InventoryMovementRepository movementRepository;

    @Override
    @Transactional
    public InventoryMovementResult execute(RegisterManualExitCommand command) {
        InventoryItem existing = itemRepository.findById(command.itemId())
                .orElseThrow(() -> new InventoryItemNotFoundException(command.itemId()));

        InventoryItem updated = InventoryItem.builder()
                .id(existing.getId())
                .name(existing.getName())
                .internalCode(existing.getInternalCode())
                .unit(existing.getUnit())
                .costPrice(existing.getCostPrice())
                .supplier(existing.getSupplier())
                .currentQuantity(existing.getCurrentQuantity().subtract(command.quantity()))
                .minimumQuantity(existing.getMinimumQuantity())
                .active(existing.isActive())
                .notes(existing.getNotes())
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        itemRepository.save(updated);

        InventoryMovement movement = InventoryMovement.builder()
                .id(UUID.randomUUID())
                .itemId(existing.getId())
                .itemName(existing.getName())
                .type(MovementType.OUT)
                .reason(MovementReason.valueOf(command.reason()))
                .quantity(command.quantity())
                .purchaseDate(command.exitDate())
                .notes(command.notes())
                .createdAt(LocalDateTime.now())
                .build();

        return InventoryUseCaseMapper.toMovementResult(movementRepository.save(movement));
    }
}
