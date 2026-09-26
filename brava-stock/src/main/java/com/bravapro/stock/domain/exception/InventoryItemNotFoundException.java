package com.bravapro.stock.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;
import java.util.UUID;

public class InventoryItemNotFoundException extends BusinessException {
    public InventoryItemNotFoundException(UUID id) {
        super("Item de estoque não encontrado: " + id);
    }
}
