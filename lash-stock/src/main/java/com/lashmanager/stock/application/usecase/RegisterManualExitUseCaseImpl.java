package com.lashmanager.stock.application.usecase;

import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.stock.domain.model.*;
import com.lashmanager.stock.domain.port.in.RegisterManualExitUseCase;
import com.lashmanager.stock.domain.port.in.RegisterPurchaseUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;
import com.lashmanager.stock.domain.port.out.InventoryMovementRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterManualExitUseCaseImpl implements RegisterManualExitUseCase {

  private final InventoryItemRepository itemRepository;
  private final InventoryMovementRepository movementRepository;

  @Override
  public RegisterPurchaseUseCase.InventoryMovementResult execute(
      RegisterManualExitCommand command) {
    InventoryItem existing =
        itemRepository
            .findById(command.itemId())
            .orElseThrow(() -> new InventoryItemNotFoundException(command.itemId()));

    itemRepository.save(
        existing.toBuilder()
            .currentQuantity(existing.getCurrentQuantity().subtract(command.quantity()))
            .updatedAt(LocalDateTime.now())
            .build());

    InventoryMovement movement =
        InventoryMovement.builder()
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
