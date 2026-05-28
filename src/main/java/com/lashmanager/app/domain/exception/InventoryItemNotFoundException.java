package com.lashmanager.app.domain.exception;

import java.util.UUID;

public class InventoryItemNotFoundException extends DomainException {
    public InventoryItemNotFoundException(UUID id) {
        super("Item de estoque não encontrado: " + id);
    }
}
