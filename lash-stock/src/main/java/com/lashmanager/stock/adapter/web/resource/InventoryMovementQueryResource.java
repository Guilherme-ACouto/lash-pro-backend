package com.lashmanager.stock.adapter.web.resource;

import com.lashmanager.stock.adapter.web.dto.InventoryMovementResponse;
import com.lashmanager.stock.domain.port.in.InventoryMovementQueryService;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** Só leitura do agregado {@code InventoryMovement} — comando mora em {@link InventoryMovementResource}. */
@RestController
@RequestMapping("/api/inventory/items")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class InventoryMovementQueryResource {

    private final InventoryMovementQueryService inventoryMovementQueryService;

    @GetMapping("/{id}/movements")
    public ResponseEntity<Page<InventoryMovementResponse>> listMovements(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(
                inventoryMovementQueryService.listByItemId(id, pageable).map(InventoryMovementResponse::from));
    }
}
