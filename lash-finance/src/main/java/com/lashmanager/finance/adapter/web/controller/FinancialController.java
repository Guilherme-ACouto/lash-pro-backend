package com.lashmanager.finance.adapter.web.controller;

import com.lashmanager.finance.adapter.web.dto.CreateFinancialEntryRequest;
import com.lashmanager.finance.adapter.web.dto.FinancialEntryResponse;
import com.lashmanager.finance.adapter.web.dto.FinancialSummaryResponse;
import com.lashmanager.finance.adapter.web.dto.UpdateFinancialEntryRequest;
import com.lashmanager.finance.domain.port.in.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/financial")
@RequiredArgsConstructor
public class FinancialController {

    private final CreateFinancialEntryUseCase createUseCase;
    private final UpdateFinancialEntryUseCase updateUseCase;
    private final DeleteFinancialEntryUseCase deleteUseCase;
    private final ListFinancialEntriesUseCase listUseCase;
    private final ToggleFinancialEntryPaidUseCase toggleUseCase;
    private final GetFinancialSummaryUseCase summaryUseCase;

    @GetMapping("/summary")
    public ResponseEntity<FinancialSummaryResponse> summary() {
        return ResponseEntity.ok(FinancialSummaryResponse.from(summaryUseCase.execute()));
    }

    @GetMapping
    public ResponseEntity<Page<FinancialEntryResponse>> list(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String expenseType,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        LocalDate start = from != null ? from : LocalDate.now().withDayOfMonth(1);
        LocalDate end = to != null ? to : LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1);
        return ResponseEntity.ok(
                listUseCase.execute(new ListFinancialEntriesUseCase.ListQuery(
                        start, end, category, expenseType, type, page, size
                )).map(FinancialEntryResponse::from)
        );
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> categories() {
        return ResponseEntity.ok(listUseCase.findDistinctCategories());
    }

    @PostMapping
    public ResponseEntity<FinancialEntryResponse> create(@Valid @RequestBody CreateFinancialEntryRequest req) {
        var result = createUseCase.execute(new CreateFinancialEntryUseCase.CreateCommand(
                req.type(), req.expenseType(), req.description(), req.amount(),
                req.dueDate(), req.paymentDate(), req.category(),
                req.paymentMethod(), req.receivedFrom(), req.notes()
        ));
        return ResponseEntity
                .created(URI.create("/api/financial/" + result.id()))
                .body(FinancialEntryResponse.from(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialEntryResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFinancialEntryRequest req
    ) {
        var result = updateUseCase.execute(id, new UpdateFinancialEntryUseCase.UpdateCommand(
                req.type(), req.expenseType(), req.description(), req.amount(),
                req.dueDate(), req.paymentDate(), req.category(),
                req.paymentMethod(), req.receivedFrom(), req.notes()
        ));
        return ResponseEntity.ok(FinancialEntryResponse.from(result));
    }

    @PatchMapping("/{id}/toggle-paid")
    public ResponseEntity<FinancialEntryResponse> togglePaid(@PathVariable UUID id) {
        return ResponseEntity.ok(FinancialEntryResponse.from(toggleUseCase.execute(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
