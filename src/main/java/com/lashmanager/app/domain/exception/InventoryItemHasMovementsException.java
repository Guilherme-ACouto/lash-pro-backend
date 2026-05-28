package com.lashmanager.app.domain.exception;

import java.util.UUID;

public class InventoryItemHasMovementsException extends BusinessException {
    public InventoryItemHasMovementsException(UUID id) {
        super("Este item possui movimentações e não pode ser excluído — desative-o em vez de excluir");
    }
}
