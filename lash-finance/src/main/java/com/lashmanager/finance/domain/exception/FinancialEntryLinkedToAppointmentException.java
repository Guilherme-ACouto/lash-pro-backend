package com.lashmanager.finance.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;

public class FinancialEntryLinkedToAppointmentException extends BusinessException {
  public FinancialEntryLinkedToAppointmentException() {
    super("Lançamento vinculado a um agendamento não pode ser excluído manualmente.");
  }
}
