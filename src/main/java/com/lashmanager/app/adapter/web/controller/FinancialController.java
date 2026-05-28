package com.lashmanager.app.adapter.web.controller;

import com.lashmanager.app.adapter.web.dto.CreateFinancialEntryRequest;
import com.lashmanager.app.adapter.web.dto.FinancialEntryResponse;
import com.lashmanager.app.adapter.web.dto.FinancialSummaryResponse;
import com.lashmanager.app.adapter.web.dto.UpdateFinancialEntryRequest;
import com.lashmanager.app.domain.model.MonthlyFinancialStat;
import com.lashmanager.app.domain.port.in.CreateFinancialEntryUseCase;
import com.lashmanager.app.domain.port.in.DeleteFinancialEntryUseCase;
import com.lashmanager.app.domain.port.in.GetFinancialSummaryUseCase;
import com.lashmanager.app.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.app.domain.port.in.ToggleFinancialEntryPaidUseCase;
import com.lashmanager.app.domain.port.in.UpdateFinancialEntryUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/financial")
@RequiredArgsConstructor
public class FinancialController {

    private final GetFinancialSummaryUseCase getSummaryUseCase;
    private final ListFinancialEntriesUseCase listEntriesUseCase;
    private final CreateFinancialEntryUseCase createUseCase;
    private final UpdateFinancialEntryUseCase updateUseCase;
    private final DeleteFinancialEntryUseCase deleteUseCase;
    private final ToggleFinancialEntryPaidUseCase togglePaidUseCase;

    @GetMapping("/summary")
    public ResponseEntity<FinancialSummaryResponse> getSummary() {
        GetFinancialSummaryUseCase.SummaryResult result = getSummaryUseCase.execute();
        return ResponseEntity.ok(toSummaryResponse(result));
    }

    @GetMapping("/entries")
    public ResponseEntity<Page<FinancialEntryResponse>> listEntries(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String expenseType,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<ListFinancialEntriesUseCase.EntryResult> results = listEntriesUseCase.execute(
                new ListFinancialEntriesUseCase.ListQuery(from, to, category, expenseType, type, page, size));

        return ResponseEntity.ok(results.map(this::toEntryResponse));
    }

    @PostMapping("/entries")
    public ResponseEntity<FinancialEntryResponse> create(@Valid @RequestBody CreateFinancialEntryRequest req) {
        ListFinancialEntriesUseCase.EntryResult result = createUseCase.execute(
                new CreateFinancialEntryUseCase.CreateCommand(
                        req.type(), req.expenseType(), req.description(), req.amount(),
                        req.dueDate(), req.paymentDate(), req.category(),
                        req.paymentMethod(), req.receivedFrom(), req.notes()));
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntryResponse(result));
    }

    @PutMapping("/entries/{id}")
    public ResponseEntity<FinancialEntryResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFinancialEntryRequest req) {
        ListFinancialEntriesUseCase.EntryResult result = updateUseCase.execute(
                new UpdateFinancialEntryUseCase.UpdateCommand(
                        id, req.description(), req.amount(), req.dueDate(), req.paymentDate(),
                        req.category(), req.expenseType(), req.paymentMethod(),
                        req.receivedFrom(), req.notes()));
        return ResponseEntity.ok(toEntryResponse(result));
    }

    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/entries/{id}/toggle-paid")
    public ResponseEntity<FinancialEntryResponse> togglePaid(@PathVariable UUID id) {
        ListFinancialEntriesUseCase.EntryResult result = togglePaidUseCase.execute(id);
        return ResponseEntity.ok(toEntryResponse(result));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> listCategories() {
        return ResponseEntity.ok(listEntriesUseCase.findDistinctCategories());
    }

    private FinancialEntryResponse toEntryResponse(ListFinancialEntriesUseCase.EntryResult r) {
        return new FinancialEntryResponse(
                r.id() != null ? r.id().toString() : null,
                r.type(),
                r.expenseType(),
                r.description(),
                r.amount(),
                r.dueDate() != null ? r.dueDate().toString() : null,
                r.paymentDate() != null ? r.paymentDate().toString() : null,
                r.status(),
                r.category(),
                r.paymentMethod(),
                r.counterpart(),
                r.notes(),
                r.linkedToAppointment(),
                r.appointmentId() != null ? r.appointmentId().toString() : null
        );
    }

    private FinancialSummaryResponse toSummaryResponse(GetFinancialSummaryUseCase.SummaryResult r) {
        List<FinancialSummaryResponse.MonthlyStatDTO> series = r.monthlySeries().stream()
                .map(s -> new FinancialSummaryResponse.MonthlyStatDTO(
                        s.year(), s.month(), s.incomeTotal(), s.expenseTotal()))
                .toList();

        return new FinancialSummaryResponse(
                r.predictedMonthResult(),
                r.currentBalance(),
                r.incomeReceived(),
                r.incomePredicted(),
                r.expensePaid(),
                r.expensePredicted(),
                series
        );
    }
}
