package com.lashmanager.stock.application.service;

import com.lashmanager.stock.application.command.UpdateInventoryItemCommand;
import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.in.UpdateInventoryItemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateInventoryItemApplicationService {

  private final UpdateInventoryItemUseCase updateInventoryItemUseCase;

  public CreateInventoryItemUseCase.InventoryItemResult when(UpdateInventoryItemCommand command) {
    return updateInventoryItemUseCase.execute(command.toDomainCommand());
  }
}
