package com.lashmanager.app.domain.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface UpdateFinancialEntryUseCase {

    record UpdateCommand(
            UUID id,
            String description,
            BigDecimal amount,
            LocalDate dueDate,
            LocalDate paymentDate,
            String category,
            String expenseType,
            String paymentMethod,
            String receivedFrom,
            String notes
    ) {}

    ListFinancialEntriesUseCase.EntryResult execute(UpdateCommand command);
}
