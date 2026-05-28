package com.lashmanager.app.domain.exception;

import java.util.UUID;

public class FinancialEntryNotFoundException extends RuntimeException {
    public FinancialEntryNotFoundException(UUID id) {
        super("Lançamento financeiro não encontrado: " + id);
    }
}
