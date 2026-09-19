package com.lashmanager.stock.adapter.web.resource;

import com.lashmanager.stock.domain.model.InventoryItem;
import com.lashmanager.stock.domain.port.in.InventoryItemQueryService;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** Só leitura do agregado {@code InventoryItem} — comando mora em {@link InventoryItemResource}. */
@RestController
@RequestMapping("/api/inventory/items")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class InventoryItemQueryResource {

    private final InventoryItemQueryService inventoryItemQueryService;

    @GetMapping
    public ResponseEntity<Page<InventoryItem>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String filter,
            Pageable pageable) {
        return ResponseEntity.ok(inventoryItemQueryService.list(search, status, filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(inventoryItemQueryService.getById(id));
    }
}
