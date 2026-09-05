package com.lashmanager.stock.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;

public class InventoryItemHasMovementsException extends BusinessException {
  public InventoryItemHasMovementsException() {
    super("Item possui movimentações e não pode ser excluído. Desative-o em vez disso.");
  }
}
