package com.bravapro.stock.application.query;

import com.bravapro.stock.domain.model.InventoryMovement;
import com.bravapro.stock.domain.port.in.InventoryMovementQueryService;
import com.bravapro.stock.domain.port.out.InventoryMovementQueryRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryMovementQueryServiceImpl implements InventoryMovementQueryService {

    private final InventoryMovementQueryRepository movementQueryRepository;

    @Override
    public Page<InventoryMovement> listByItemId(UUID itemId, Pageable pageable) {
        return movementQueryRepository.findByItemId(itemId, pageable);
    }
}
