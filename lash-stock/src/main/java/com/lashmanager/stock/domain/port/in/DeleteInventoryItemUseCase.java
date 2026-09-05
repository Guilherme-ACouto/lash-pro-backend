package com.lashmanager.stock.domain.port.in;

import java.util.UUID;

public interface DeleteInventoryItemUseCase {
  void execute(UUID id);
}
