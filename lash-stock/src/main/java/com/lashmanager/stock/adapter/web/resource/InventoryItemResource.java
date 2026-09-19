package com.lashmanager.stock.adapter.web.resource;

import com.lashmanager.core.infrastructure.web.RestUtils;
import com.lashmanager.stock.application.command.CreateInventoryItemCommand;
import com.lashmanager.stock.application.command.DeactivateInventoryItemCommand;
import com.lashmanager.stock.application.command.DeleteInventoryItemCommand;
import com.lashmanager.stock.application.command.ReactivateInventoryItemCommand;
import com.lashmanager.stock.application.command.UpdateInventoryItemCommand;
import com.lashmanager.stock.application.service.InventoryItemApplicationService;
import com.lashmanager.stock.domain.model.InventoryItem;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Só comando do agregado {@code InventoryItem} — leitura mora em
 * {@link InventoryItemQueryResource} (mesma URL base). Rotas alinhadas em
 * {@code /api/inventory/items} — corrigido nesta refatoração (ver commit da consolidação):
 * o {@code InventoryController} antigo usava {@code /api/inventory} (sem {@code /items}) e
 * {@code /activate} (o frontend chama {@code /reactivate}), nunca batia com o frontend.
 */
@RestController
@RequestMapping("/api/inventory/items")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class InventoryItemResource {

    private static final String ENTITY_NAME = "inventoryItem";

    private final InventoryItemApplicationService inventoryItemApplicationService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateInventoryItemCommand command) {
        InventoryItem item = inventoryItemApplicationService.when(command);
        return RestUtils.message().created(ENTITY_NAME, item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateInventoryItemCommand command) {
        inventoryItemApplicationService.when(command.id(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        inventoryItemApplicationService.when(new DeleteInventoryItemCommand(id));
        return RestUtils.message().deleted(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        inventoryItemApplicationService.when(new DeactivateInventoryItemCommand(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        inventoryItemApplicationService.when(new ReactivateInventoryItemCommand(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }
}
