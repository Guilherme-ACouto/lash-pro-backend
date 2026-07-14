package com.lashmanager.stock.application.usecase;

import com.lashmanager.stock.domain.port.in.ListMovementsUseCase;
import com.lashmanager.stock.domain.port.in.RegisterPurchaseUseCase;
import com.lashmanager.stock.domain.port.out.InventoryMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RequiredArgsConstructor
public class ListMovementsUseCaseImpl implements ListMovementsUseCase {

    private final InventoryMovementRepository movementRepository;

    @Override
    public Page<RegisterPurchaseUseCase.InventoryMovementResult> execute(UUID itemId, Pageable pageable) {
        return movementRepository.findByItemId(itemId, pageable)
                .map(InventoryUseCaseMapper::toMovementResult);
    }
}
