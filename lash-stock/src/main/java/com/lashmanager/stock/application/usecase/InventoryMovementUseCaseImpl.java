package com.lashmanager.stock.application.usecase;

import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.model.FinancialEntryExpenseType;
import com.lashmanager.finance.domain.model.FinancialEntryStatus;
import com.lashmanager.finance.domain.model.FinancialEntryType;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import com.lashmanager.stock.application.command.RegisterManualExitCommand;
import com.lashmanager.stock.application.command.RegisterPurchaseCommand;
import com.lashmanager.stock.domain.model.InventoryItem;
import com.lashmanager.stock.domain.model.InventoryMovement;
import com.lashmanager.stock.domain.model.MovementReason;
import com.lashmanager.stock.domain.model.MovementType;
import com.lashmanager.stock.domain.model.PurchasePaymentType;
import com.lashmanager.stock.domain.port.in.InventoryMovementUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;
import com.lashmanager.stock.domain.port.out.InventoryMovementRepository;

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
