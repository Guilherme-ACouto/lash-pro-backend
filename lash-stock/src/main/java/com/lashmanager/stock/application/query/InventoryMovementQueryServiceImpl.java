package com.lashmanager.stock.application.query;

import com.lashmanager.stock.domain.model.InventoryMovement;
import com.lashmanager.stock.domain.port.in.InventoryMovementQueryService;
import com.lashmanager.stock.domain.port.out.InventoryMovementQueryRepository;

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
