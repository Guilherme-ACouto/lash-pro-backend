package com.lashmanager.finance.domain.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CreateFinancialEntryUseCase {

  record CreateCommand(
      String type,
      String expenseType,
      String description,
      BigDecimal amount,
      LocalDate dueDate,
      LocalDate paymentDate,
      String category,
      String paymentMethod,
      String receivedFrom,
      String notes) {}

  ListFinancialEntriesUseCase.EntryResult execute(CreateCommand command);
}
