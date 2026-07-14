package com.lashmanager.finance.domain.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface UpdateFinancialEntryUseCase {

    record UpdateCommand(
            String type,
            String expenseType,
            String description,
            BigDecimal amount,
            LocalDate dueDate,
            LocalDate paymentDate,
            String category,
            String paymentMethod,
            String receivedFrom,
            String notes
    ) {}

    ListFinancialEntriesUseCase.EntryResult execute(UUID id, UpdateCommand command);
}
