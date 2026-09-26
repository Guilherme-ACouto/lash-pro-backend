package com.bravapro.stock.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;

public class InventoryItemCodeAlreadyExistsException extends BusinessException {
    public InventoryItemCodeAlreadyExistsException(String code) {
        super("Já existe um item com o código: " + code);
    }
}
