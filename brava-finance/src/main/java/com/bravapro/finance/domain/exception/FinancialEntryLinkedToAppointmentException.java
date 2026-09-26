package com.bravapro.finance.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;

public class FinancialEntryLinkedToAppointmentException extends BusinessException {
    public FinancialEntryLinkedToAppointmentException() {
        super("Lançamento vinculado a um agendamento não pode ser excluído manualmente.");
    }
}
