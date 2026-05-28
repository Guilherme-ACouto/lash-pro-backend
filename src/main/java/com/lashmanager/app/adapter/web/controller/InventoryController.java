package com.lashmanager.app.adapter.web.controller;

import com.lashmanager.app.adapter.web.dto.*;
import com.lashmanager.app.domain.port.in.*;
import com.lashmanager.app.domain.port.in.CreateInventoryItemUseCase.InventoryItemResult;
import com.lashmanager.app.domain.port.in.RegisterPurchaseUseCase.InventoryMovementResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/items")
@RequiredArgsConstructor
public class InventoryController {

    private final CreateInventoryItemUseCase createUseCase;
    private final UpdateInventoryItemUseCase updateUseCase;
    private final DeleteInventoryItemUseCase deleteUseCase;
    private final ListInventoryItemsUseCase listUseCase;
    private final GetInventoryItemUseCase getUseCase;
    private final DeactivateInventoryItemUseCase deactivateUseCase;
    private final RegisterPurchaseUseCase registerPurchaseUseCase;
    private final RegisterManualExitUseCase registerExitUseCase;
    private final ListMovementsUseCase listMovementsUseCase;

    @PostMapping
    public ResponseEntity<InventoryItemResponse> create(@Valid @RequestBody CreateInventoryItemRequest req) {
        var result = createUseCase.execute(new CreateInventoryItemUseCase.CreateInventoryItemCommand(
                req.name(), req.internalCode(), req.unit(), req.costPrice(),
                req.supplier(), req.currentQuantity(), req.minimumQuantity(), req.notes()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(toItemResponse(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItemResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateInventoryItemRequest req
    ) {
        var result = updateUseCase.execute(new UpdateInventoryItemUseCase.UpdateInventoryItemCommand(
                id, req.name(), req.unit(), req.costPrice(), req.supplier(), req.minimumQuantity(), req.notes()
        ));
        return ResponseEntity.ok(toItemResponse(result));
    }

    @GetMapping
    public ResponseEntity<Page<InventoryItemResponse>> list(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "ACTIVE") String status,
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var query = new ListInventoryItemsUseCase.ListInventoryItemsQuery(search, status, filter);
        return ResponseEntity.ok(listUseCase.execute(query, PageRequest.of(page, size)).map(this::toItemResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItemResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(toItemResponse(getUseCase.execute(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<InventoryItemResponse> deactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(toItemResponse(deactivateUseCase.execute(id, false)));
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<InventoryItemResponse> reactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(toItemResponse(deactivateUseCase.execute(id, true)));
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<RegisterPurchaseResponse> purchase(
            @PathVariable UUID id,
            @Valid @RequestBody RegisterPurchaseRequest req
    ) {
        var result = registerPurchaseUseCase.execute(new RegisterPurchaseUseCase.RegisterPurchaseCommand(
                id, req.quantity(), req.unitCost(), req.supplier(),
                req.purchaseDate(), req.paymentType(), req.dueDate(), req.notes()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterPurchaseResponse(
                toItemResponse(result.item()),
                toMovementResponse(result.movement()),
                result.financialEntryId()
        ));
    }

    @PostMapping("/{id}/exit")
    public ResponseEntity<InventoryMovementResponse> exit(
            @PathVariable UUID id,
            @Valid @RequestBody RegisterManualExitRequest req
    ) {
        var result = registerExitUseCase.execute(new RegisterManualExitUseCase.RegisterManualExitCommand(
                id, req.quantity(), req.reason(), req.notes(), req.exitDate()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(toMovementResponse(result));
    }

    @GetMapping("/{id}/movements")
    public ResponseEntity<Page<InventoryMovementResponse>> movements(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(listMovementsUseCase.execute(id, PageRequest.of(page, size))
                .map(this::toMovementResponse));
    }

    private InventoryItemResponse toItemResponse(InventoryItemResult r) {
        return new InventoryItemResponse(
                r.id(), r.name(), r.internalCode(), r.unit(), r.costPrice(), r.supplier(),
                r.currentQuantity(), r.minimumQuantity(), r.active(), r.belowMinimum(),
                r.outOfStock(), r.notes(), r.createdAt()
        );
    }

    private InventoryMovementResponse toMovementResponse(InventoryMovementResult r) {
        return new InventoryMovementResponse(
                r.id(), r.itemId(), r.itemName(), r.type(), r.reason(),
                r.quantity(), r.unitCost(), r.totalCost(), r.supplier(),
                r.purchaseDate(), r.paymentType(), r.dueDate(),
                r.financialEntryId(), r.notes(), r.createdAt()
        );
    }
}
