package com.lashmanager.stock.adapter.web.controller;

import com.lashmanager.stock.adapter.web.dto.*;
import com.lashmanager.stock.application.command.CreateInventoryItemCommand;
import com.lashmanager.stock.application.command.DeleteInventoryItemCommand;
import com.lashmanager.stock.application.command.RegisterManualExitCommand;
import com.lashmanager.stock.application.command.RegisterPurchaseCommand;
import com.lashmanager.stock.application.command.SetInventoryItemActiveCommand;
import com.lashmanager.stock.application.command.UpdateInventoryItemCommand;
import com.lashmanager.stock.application.service.CreateInventoryItemApplicationService;
import com.lashmanager.stock.application.service.DeleteInventoryItemApplicationService;
import com.lashmanager.stock.application.service.RegisterManualExitApplicationService;
import com.lashmanager.stock.application.service.RegisterPurchaseApplicationService;
import com.lashmanager.stock.application.service.UpdateInventoryItemApplicationService;
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

    private final CreateInventoryItemApplicationService createItemApplicationService;
    private final UpdateInventoryItemApplicationService updateItemApplicationService;
    private final DeleteInventoryItemApplicationService deleteItemApplicationService;
    private final RegisterPurchaseApplicationService registerPurchaseApplicationService;
    private final RegisterManualExitApplicationService registerManualExitApplicationService;
    private final GetInventoryItemUseCase getItemUseCase;
    private final ListInventoryItemsUseCase listItemsUseCase;
    private final ListMovementsUseCase listMovementsUseCase;

    @PostMapping
    public ResponseEntity<InventoryItemResult> create(@Valid @RequestBody CreateInventoryItemRequest req) {
        var result = createItemApplicationService.when(new CreateInventoryItemCommand(
                req.name(), req.internalCode(), req.unit(), req.costPrice(),
                req.supplier(), req.currentQuantity(), req.minimumQuantity(), req.notes()
        ));
        return ResponseEntity.status(201).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItemResult> update(@PathVariable UUID id,
                                                      @Valid @RequestBody UpdateInventoryItemRequest req) {
        var result = updateItemApplicationService.when(new UpdateInventoryItemCommand(
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
        deleteItemApplicationService.when(new DeleteInventoryItemCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<InventoryItemResult> activate(@PathVariable UUID id) {
        return ResponseEntity.ok(deleteItemApplicationService.when(new SetInventoryItemActiveCommand(id, true)));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<InventoryItemResult> deactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(deleteItemApplicationService.when(new SetInventoryItemActiveCommand(id, false)));
    }

    @PostMapping("/{id}/purchases")
    public ResponseEntity<RegisterPurchaseUseCase.RegisterPurchaseResult> registerPurchase(
            @PathVariable UUID id, @Valid @RequestBody RegisterPurchaseRequest req) {
        var result = registerPurchaseApplicationService.when(new RegisterPurchaseCommand(
                id, req.quantity(), req.unitCost(), req.supplier(),
                req.purchaseDate(), req.paymentType(), req.dueDate(), req.notes()
        ));
        return ResponseEntity.status(201).body(result);
    }

    @PostMapping("/{id}/exits")
    public ResponseEntity<InventoryMovementResult> registerExit(
            @PathVariable UUID id, @Valid @RequestBody RegisterManualExitRequest req) {
        var result = registerManualExitApplicationService.when(new RegisterManualExitCommand(
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
