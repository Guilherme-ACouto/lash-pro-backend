package com.lashmanager.stock.domain.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface UpdateInventoryItemUseCase {

  record UpdateInventoryItemCommand(
      UUID id,
      String name,
      String unit,
      BigDecimal costPrice,
      String supplier,
      BigDecimal minimumQuantity,
      String notes) {}

  CreateInventoryItemUseCase.InventoryItemResult execute(UpdateInventoryItemCommand command);
}
