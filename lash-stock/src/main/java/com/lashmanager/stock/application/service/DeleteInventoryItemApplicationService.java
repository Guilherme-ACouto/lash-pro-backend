package com.lashmanager.stock.application.service;

import com.lashmanager.stock.application.command.DeleteInventoryItemCommand;
import com.lashmanager.stock.application.command.SetInventoryItemActiveCommand;
import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.in.DeactivateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.in.DeleteInventoryItemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteInventoryItemApplicationService {

  private final DeleteInventoryItemUseCase deleteInventoryItemUseCase;
  private final DeactivateInventoryItemUseCase deactivateInventoryItemUseCase;

  public void when(DeleteInventoryItemCommand command) {
    deleteInventoryItemUseCase.execute(command.getId());
  }

  public CreateInventoryItemUseCase.InventoryItemResult when(
      SetInventoryItemActiveCommand command) {
    return deactivateInventoryItemUseCase.execute(command.getId(), command.isActivate());
  }
}
