package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.port.in.ListMovementsUseCase;
import com.lashmanager.app.domain.port.in.RegisterPurchaseUseCase.InventoryMovementResult;
import com.lashmanager.app.domain.port.out.InventoryMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListMovementsUseCaseImpl implements ListMovementsUseCase {

    private final InventoryMovementRepository movementRepository;

    @Override
    public Page<InventoryMovementResult> execute(UUID itemId, Pageable pageable) {
        return movementRepository.findByItemId(itemId, pageable)
                .map(InventoryUseCaseMapper::toMovementResult);
    }
}
