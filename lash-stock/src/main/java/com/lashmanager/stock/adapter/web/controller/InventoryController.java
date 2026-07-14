package com.lashmanager.stock.adapter.web.controller;

import com.lashmanager.stock.adapter.web.dto.*;
import com.lashmanager.stock.domain.port.in.*;
import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase.*;
import com.lashmanager.stock.domain.port.in.RegisterPurchaseUseCase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class InventoryController {

    private final CreateInventoryItemUseCase createItemUseCase;
    private final UpdateInventoryItemUseCase updateItemUseCase;
    private final GetInventoryItemUseCase getItemUseCase;
    private final ListInventoryItemsUseCase listItemsUseCase;
    private final DeleteInventoryItemUseCase deleteItemUseCase;
    private final DeactivateInventoryItemUseCase deactivateItemUseCase;
    private final RegisterPurchaseUseCase registerPurchaseUseCase;
    private final RegisterManualExitUseCase registerManualExitUseCase;
    private final ListMovementsUseCase listMovementsUseCase;

    @PostMapping
    public ResponseEntity<InventoryItemResult> create(@Valid @RequestBody CreateInventoryItemRequest req) {
        var result = createItemUseCase.execute(new CreateInventoryItemUseCase.CreateInventoryItemCommand(
                req.name(), req.internalCode(), req.unit(), req.costPrice(),
                req.supplier(), req.currentQuantity(), req.minimumQuantity(), req.notes()
        ));
        return ResponseEntity.status(201).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItemResult> update(@PathVariable UUID id,
                                                      @Valid @RequestBody UpdateInventoryItemRequest req) {
        var result = updateItemUseCase.execute(new UpdateInventoryItemUseCase.UpdateInventoryItemCommand(
                id, req.name(), req.unit(), req.costPrice(), req.supplier(), req.minimumQuantity(), req.notes()
        ));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItemResult> get(@PathVariable UUID id) {
        return ResponseEntity.ok(getItemUseCase.execute(id));
    }

    @GetMapping
    public ResponseEntity<Page<InventoryItemResult>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String filter,
            Pageable pageable) {
        return ResponseEntity.ok(listItemsUseCase.execute(
                new ListInventoryItemsUseCase.ListInventoryItemsQuery(search, status, filter), pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteItemUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<InventoryItemResult> activate(@PathVariable UUID id) {
        return ResponseEntity.ok(deactivateItemUseCase.execute(id, true));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<InventoryItemResult> deactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(deactivateItemUseCase.execute(id, false));
    }

    @PostMapping("/{id}/purchases")
    public ResponseEntity<RegisterPurchaseUseCase.RegisterPurchaseResult> registerPurchase(
            @PathVariable UUID id, @Valid @RequestBody RegisterPurchaseRequest req) {
        var result = registerPurchaseUseCase.execute(new RegisterPurchaseUseCase.RegisterPurchaseCommand(
                id, req.quantity(), req.unitCost(), req.supplier(),
                req.purchaseDate(), req.paymentType(), req.dueDate(), req.notes()
        ));
        return ResponseEntity.status(201).body(result);
    }

    @PostMapping("/{id}/exits")
    public ResponseEntity<InventoryMovementResult> registerExit(
            @PathVariable UUID id, @Valid @RequestBody RegisterManualExitRequest req) {
        var result = registerManualExitUseCase.execute(new RegisterManualExitUseCase.RegisterManualExitCommand(
                id, req.quantity(), req.reason(), req.notes(), req.exitDate()
        ));
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/{id}/movements")
    public ResponseEntity<Page<InventoryMovementResult>> listMovements(
            @PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(listMovementsUseCase.execute(id, pageable));
    }
}
