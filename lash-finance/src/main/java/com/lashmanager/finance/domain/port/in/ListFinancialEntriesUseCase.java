package com.lashmanager.finance.domain.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ListFinancialEntriesUseCase {

  record ListQuery(
      LocalDate from,
      LocalDate to,
      String category,
      String expenseType,
      String type,
      int page,
      int size) {}

  record EntryResult(
      UUID id,
      String type,
      String expenseType,
      String description,
      BigDecimal amount,
      LocalDate dueDate,
      LocalDate paymentDate,
      String status,
      String category,
      String paymentMethod,
      String counterpart,
      String notes,
      boolean linkedToAppointment,
      UUID appointmentId) {}

  Page<EntryResult> execute(ListQuery query);

  List<String> findDistinctCategories();
}
