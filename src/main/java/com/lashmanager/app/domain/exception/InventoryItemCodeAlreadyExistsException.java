package com.lashmanager.app.domain.exception;

public class InventoryItemCodeAlreadyExistsException extends BusinessException {
    public InventoryItemCodeAlreadyExistsException(String code) {
        super("Já existe um item com o código: " + code);
    }
}
