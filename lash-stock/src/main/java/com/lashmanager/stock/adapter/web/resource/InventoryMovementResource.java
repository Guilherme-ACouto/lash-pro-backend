package com.lashmanager.stock.adapter.web.resource;

import com.lashmanager.core.infrastructure.web.RestUtils;
import com.lashmanager.stock.application.command.RegisterManualExitCommand;
import com.lashmanager.stock.application.command.RegisterPurchaseCommand;
import com.lashmanager.stock.application.service.InventoryMovementApplicationService;
import com.lashmanager.stock.domain.model.InventoryMovement;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Só comando do agregado {@code InventoryMovement} — leitura mora em
 * {@link InventoryMovementQueryResource}. Nasce sob {@code /api/inventory/items/{id}/...} porque
 * a URL é aninhada no item (movimento só existe em função de um item), mesmo sendo um Resource
 * de agregado diferente do {@link InventoryItemResource}.
 */
@RestController
@RequestMapping("/api/inventory/items")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class InventoryMovementResource {

    private static final String ENTITY_NAME = "inventoryMovement";

    private final InventoryMovementApplicationService inventoryMovementApplicationService;

    @PostMapping("/{id}/purchase")
    public ResponseEntity<Object> registerPurchase(
            @PathVariable UUID id, @Valid @RequestBody RegisterPurchaseCommand command) {
        InventoryMovement movement = inventoryMovementApplicationService.when(command.itemId(id));
        return RestUtils.message().created(ENTITY_NAME, movement);
    }

    @PostMapping("/{id}/exit")
    public ResponseEntity<Object> registerExit(
            @PathVariable UUID id, @Valid @RequestBody RegisterManualExitCommand command) {
        InventoryMovement movement = inventoryMovementApplicationService.when(command.itemId(id));
        return RestUtils.message().created(ENTITY_NAME, movement);
    }
}
