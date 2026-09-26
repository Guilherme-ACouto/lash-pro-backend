package com.bravapro.stock.adapter.web.resource;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.stock.adapter.web.dto.InventoryMovementResponse;
import com.bravapro.stock.domain.port.in.InventoryMovementQueryService;

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
public class InventoryMovementQueryResource implements QueryPermissionAware {

    private final InventoryMovementQueryService inventoryMovementQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.of(Permission.INVENTORY);
    }

    @GetMapping("/{id}/movements")
    public ResponseEntity<Page<InventoryMovementResponse>> listMovements(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(
                inventoryMovementQueryService.listByItemId(id, pageable).map(InventoryMovementResponse::from));
    }
}
