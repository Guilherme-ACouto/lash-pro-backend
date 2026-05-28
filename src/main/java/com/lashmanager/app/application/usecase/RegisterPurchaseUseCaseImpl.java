package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.app.domain.model.*;
import com.lashmanager.app.domain.port.in.RegisterPurchaseUseCase;
import com.lashmanager.app.domain.port.out.FinancialEntryRepository;
import com.lashmanager.app.domain.port.out.InventoryItemRepository;
import com.lashmanager.app.domain.port.out.InventoryMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterPurchaseUseCaseImpl implements RegisterPurchaseUseCase {

    private final InventoryItemRepository itemRepository;
    private final InventoryMovementRepository movementRepository;
    private final FinancialEntryRepository financialEntryRepository;

    @Override
    @Transactional
    public RegisterPurchaseResult execute(RegisterPurchaseCommand command) {
        InventoryItem existing = itemRepository.findById(command.itemId())
                .orElseThrow(() -> new InventoryItemNotFoundException(command.itemId()));

        // Atualiza estoque, custo e fornecedor
        String resolvedSupplier = command.supplier() != null && !command.supplier().isBlank()
                ? command.supplier() : existing.getSupplier();

        InventoryItem updatedItem = InventoryItem.builder()
                .id(existing.getId())
                .name(existing.getName())
                .internalCode(existing.getInternalCode())
                .unit(existing.getUnit())
                .costPrice(command.unitCost())
                .supplier(resolvedSupplier)
                .currentQuantity(existing.getCurrentQuantity().add(command.quantity()))
                .minimumQuantity(existing.getMinimumQuantity())
                .active(existing.isActive())
                .notes(existing.getNotes())
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        InventoryItem savedItem = itemRepository.save(updatedItem);

        // Cria entrada financeira
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

        // Registra movimentação com financialEntryId
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
