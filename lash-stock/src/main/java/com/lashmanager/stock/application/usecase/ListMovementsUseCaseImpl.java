package com.lashmanager.stock.application.usecase;

import com.lashmanager.stock.domain.port.in.ListMovementsUseCase;
import com.lashmanager.stock.domain.port.in.RegisterPurchaseUseCase;
import com.lashmanager.stock.domain.port.out.InventoryMovementQueryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListMovementsUseCaseImpl implements ListMovementsUseCase {

  private final InventoryMovementQueryRepository movementQueryRepository;

  @Override
  public Page<RegisterPurchaseUseCase.InventoryMovementResult> execute(
      UUID itemId, Pageable pageable) {
    return movementQueryRepository
        .findByItemId(itemId, pageable)
        .map(InventoryUseCaseMapper::toMovementResult);
  }
}
