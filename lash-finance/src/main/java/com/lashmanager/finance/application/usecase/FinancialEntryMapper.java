package com.lashmanager.finance.application.usecase;

import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.port.in.ListFinancialEntriesUseCase;

public class FinancialEntryMapper {

  private FinancialEntryMapper() {}

  public static ListFinancialEntriesUseCase.EntryResult toResult(
      FinancialEntry e, String counterpart) {
    return new ListFinancialEntriesUseCase.EntryResult(
        e.getId(),
        e.getType().name(),
        e.getExpenseType() != null ? e.getExpenseType().name() : null,
        e.getDescription(),
        e.getAmount(),
        e.getDueDate(),
        e.getPaymentDate(),
        e.getStatus().name(),
        e.getCategory(),
        e.getPaymentMethod(),
        counterpart,
        e.getNotes(),
        e.getAppointmentId() != null,
        e.getAppointmentId());
  }
}
