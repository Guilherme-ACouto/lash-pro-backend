package com.bravapro.finance.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;
import java.util.UUID;

public class FinancialEntryNotFoundException extends BusinessException {
    public FinancialEntryNotFoundException(UUID id) {
        super("Lançamento financeiro não encontrado: " + id);
    }
}
