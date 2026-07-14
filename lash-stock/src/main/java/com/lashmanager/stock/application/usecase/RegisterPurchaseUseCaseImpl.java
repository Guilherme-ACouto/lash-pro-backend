package com.lashmanager.stock.application.usecase;

import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.model.FinancialEntryExpenseType;
import com.lashmanager.finance.domain.model.FinancialEntryStatus;
import com.lashmanager.finance.domain.model.FinancialEntryType;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.stock.domain.model.*;
import com.lashmanager.stock.domain.port.in.RegisterPurchaseUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;
import com.lashmanager.stock.domain.port.out.InventoryMovementRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class RegisterPurchaseUseCaseImpl implements RegisterPurchaseUseCase {

    private final InventoryItemRepository itemRepository;
    private final InventoryMovementRepository movementRepository;
    private final FinancialEntryRepository financialEntryRepository;

    @Override
    public RegisterPurchaseResult execute(RegisterPurchaseCommand command) {
        InventoryItem existing = itemRepository.findById(command.itemId())
                .orElseThrow(() -> new InventoryItemNotFoundException(command.itemId()));

        String resolvedSupplier = (command.supplier() != null && !command.supplier().isBlank())
                ? command.supplier() : existing.getSupplier();

        InventoryItem updatedItem = existing.toBuilder()
                .costPrice(command.unitCost())
                .supplier(resolvedSupplier)
                .currentQuantity(existing.getCurrentQuantity().add(command.quantity()))
                .updatedAt(LocalDateTime.now())
                .build();
        InventoryItem savedItem = itemRepository.save(updatedItem);

        BigDecimal total = command.quantity().multiply(command.unitCost());
        boolean isCash = PurchasePaymentType.CASH.name().equalsIgnoreCase(command.paymentType());

        FinancialEntry expense = FinancialEntry.builder()
                .id(UUID.randomUUID())
                .type(FinancialEntryType.EXPENSE)
                .expenseType(FinancialEntryExpenseType.SUPPLY)
                .description("Compra: " + existing.getName())
                .amount(total)
                .dueDate(isCash ? command.purchaseDate() : command.dueDate())
                .paymentDate(isCash ? command.purchaseDate() : null)
                .status(isCash ? FinancialEntryStatus.PAID : FinancialEntryStatus.PENDING)
                .notes(command.notes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        FinancialEntry savedExpense = financialEntryRepository.save(expense);

        InventoryMovement movement = InventoryMovement.builder()
                .id(UUID.randomUUID())
                .itemId(existing.getId())
                .itemName(existing.getName())
                .type(MovementType.IN)
                .reason(MovementReason.PURCHASE)
                .quantity(command.quantity())
                .unitCost(command.unitCost())
                .totalCost(total)
                .supplier(resolvedSupplier)
                .purchaseDate(command.purchaseDate())
                .paymentType(isCash ? PurchasePaymentType.CASH : PurchasePaymentType.INVOICE)
                .dueDate(isCash ? null : command.dueDate())
                .financialEntryId(savedExpense.getId())
                .notes(command.notes())
                .createdAt(LocalDateTime.now())
                .build();
        InventoryMovement savedMovement = movementRepository.save(movement);

        return new RegisterPurchaseResult(
                InventoryUseCaseMapper.toItemResult(savedItem),
                InventoryUseCaseMapper.toMovementResult(savedMovement),
                savedExpense.getId().toString()
        );
    }
}
