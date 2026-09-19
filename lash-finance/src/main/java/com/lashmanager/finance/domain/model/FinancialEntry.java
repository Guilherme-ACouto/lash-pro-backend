package com.lashmanager.finance.domain.model;

import com.lashmanager.core.domain.exception.BusinessException;
import com.lashmanager.core.domain.model.DomainEntity;
import com.lashmanager.finance.application.command.UpdateFinancialEntryCommand;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FinancialEntry implements DomainEntity {
    private UUID id;
    private FinancialEntryType type;
    private FinancialEntryExpenseType expenseType;
    private String description;
    private BigDecimal amount;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private FinancialEntryStatus status;
    private UUID appointmentId;
    private String category;
    private String paymentMethod;
    private String receivedFrom;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(UpdateFinancialEntryCommand command) {
        this.type = FinancialEntryType.valueOf(command.getType());
        this.expenseType =
                command.getExpenseType() != null ? FinancialEntryExpenseType.valueOf(command.getExpenseType()) : null;
        this.description = command.getDescription();
        this.amount = command.getAmount();
        this.dueDate = command.getDueDate();
        this.paymentDate = command.getPaymentDate();
        this.status = command.getPaymentDate() != null ? FinancialEntryStatus.PAID : FinancialEntryStatus.PENDING;
        this.category = command.getCategory();
        this.paymentMethod = command.getPaymentMethod();
        this.receivedFrom = command.getReceivedFrom();
        this.notes = command.getNotes();
        this.updatedAt = LocalDateTime.now();
    }

    public void togglePaid() {
        if (this.status == FinancialEntryStatus.OVERDUE) {
            throw new BusinessException("Lançamento vencido não pode ser alternado via toggle");
        }
        if (this.type == FinancialEntryType.INCOME && this.status == FinancialEntryStatus.PAID) {
            throw new BusinessException("Receita já recebida não pode ser revertida para pendente");
        }

        if (this.status == FinancialEntryStatus.PENDING) {
            this.status = FinancialEntryStatus.PAID;
            this.paymentDate = LocalDate.now();
        } else {
            this.status = FinancialEntryStatus.PENDING;
            this.paymentDate = null;
        }
        this.updatedAt = LocalDateTime.now();
    }
}
