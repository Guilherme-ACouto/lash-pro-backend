package com.lashmanager.stock.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;
import java.util.UUID;

public class InventoryItemNotFoundException extends BusinessException {
    public InventoryItemNotFoundException(UUID id) {
        super("Item de estoque não encontrado: " + id);
    }
}
