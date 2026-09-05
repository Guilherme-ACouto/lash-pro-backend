package com.lashmanager.stock.application.usecase;

import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.in.GetInventoryItemUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemQueryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetInventoryItemUseCaseImpl implements GetInventoryItemUseCase {

  private final InventoryItemQueryRepository itemQueryRepository;

  @Override
  public CreateInventoryItemUseCase.InventoryItemResult execute(UUID id) {
    return itemQueryRepository
        .findById(id)
        .map(InventoryUseCaseMapper::toItemResult)
        .orElseThrow(() -> new InventoryItemNotFoundException(id));
  }
}
