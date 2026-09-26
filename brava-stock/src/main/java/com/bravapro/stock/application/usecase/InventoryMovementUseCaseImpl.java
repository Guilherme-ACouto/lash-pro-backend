package com.bravapro.stock.application.usecase;

import com.bravapro.finance.domain.model.FinancialEntry;
import com.bravapro.finance.domain.model.FinancialEntryExpenseType;
import com.bravapro.finance.domain.model.FinancialEntryStatus;
import com.bravapro.finance.domain.model.FinancialEntryType;
import com.bravapro.finance.domain.port.out.FinancialEntryRepository;
import com.bravapro.stock.application.command.RegisterManualExitCommand;
import com.bravapro.stock.application.command.RegisterPurchaseCommand;
import com.bravapro.stock.domain.model.InventoryItem;
import com.bravapro.stock.domain.model.InventoryMovement;
import com.bravapro.stock.domain.model.MovementReason;
import com.bravapro.stock.domain.model.MovementType;
import com.bravapro.stock.domain.model.PurchasePaymentType;
import com.bravapro.stock.domain.port.in.InventoryMovementUseCase;
import com.bravapro.stock.domain.port.out.InventoryItemRepository;
import com.bravapro.stock.domain.port.out.InventoryMovementRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryMovementUseCaseImpl implements InventoryMovementUseCase {

    private final InventoryItemRepository itemRepository;
    private final InventoryMovementRepository movementRepository;
    private final FinancialEntryRepository financialEntryRepository;

    @Override
    public InventoryMovement registerPurchase(InventoryItem item, RegisterPurchaseCommand command) {
        String resolvedSupplier = (command.getSupplier() != null && !command.getSupplier().isBlank())
                ? command.getSupplier()
                : item.getSupplier();

        item.registerPurchase(command.getQuantity(), command.getUnitCost(), resolvedSupplier);
        itemRepository.save(item);

        BigDecimal total = command.getQuantity().multiply(command.getUnitCost());
        boolean isCash = PurchasePaymentType.CASH.name().equalsIgnoreCase(command.getPaymentType());

        FinancialEntry expense = FinancialEntry.builder()
                .id(UUID.randomUUID())
                .type(FinancialEntryType.EXPENSE)
                .expenseType(FinancialEntryExpenseType.SUPPLY)
                .description("Compra: " + item.getName())
                .amount(total)
                .dueDate(isCash ? command.getPurchaseDate() : command.getDueDate())
                .paymentDate(isCash ? command.getPurchaseDate() : null)
                .status(isCash ? FinancialEntryStatus.PAID : FinancialEntryStatus.PENDING)
                .notes(command.getNotes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        FinancialEntry savedExpense = financialEntryRepository.save(expense);

        InventoryMovement movement = InventoryMovement.builder()
                .id(UUID.randomUUID())
                .itemId(item.getId())
                .itemName(item.getName())
                .type(MovementType.IN)
                .reason(MovementReason.PURCHASE)
                .quantity(command.getQuantity())
                .unitCost(command.getUnitCost())
                .totalCost(total)
                .supplier(resolvedSupplier)
                .purchaseDate(command.getPurchaseDate())
                .paymentType(isCash ? PurchasePaymentType.CASH : PurchasePaymentType.INVOICE)
                .dueDate(isCash ? null : command.getDueDate())
                .financialEntryId(savedExpense.getId())
                .notes(command.getNotes())
                .createdAt(LocalDateTime.now())
                .build();

        return movementRepository.save(movement);
    }

    @Override
    public InventoryMovement registerManualExit(InventoryItem item, RegisterManualExitCommand command) {
        item.registerManualExit(command.getQuantity());
        itemRepository.save(item);

        InventoryMovement movement = InventoryMovement.builder()
                .id(UUID.randomUUID())
                .itemId(item.getId())
                .itemName(item.getName())
                .type(MovementType.OUT)
                .reason(MovementReason.valueOf(command.getReason()))
                .quantity(command.getQuantity())
                .purchaseDate(command.getExitDate())
                .notes(command.getNotes())
                .createdAt(LocalDateTime.now())
                .build();

        return movementRepository.save(movement);
    }
}
