package com.lashmanager.finance.adapter.web.resource;

import com.lashmanager.core.infrastructure.web.RestUtils;
import com.lashmanager.finance.application.command.CreateFinancialEntryCommand;
import com.lashmanager.finance.application.command.DeleteFinancialEntryCommand;
import com.lashmanager.finance.application.command.ToggleFinancialEntryPaidCommand;
import com.lashmanager.finance.application.command.UpdateFinancialEntryCommand;
import com.lashmanager.finance.application.service.FinancialEntryApplicationService;
import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.model.FinancialEntryDetails;
import com.lashmanager.finance.domain.model.FinancialEntryFilter;
import com.lashmanager.finance.domain.model.FinancialSummary;
import com.lashmanager.finance.domain.port.in.FinancialEntryQueryService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financial")
@RequiredArgsConstructor
public class FinancialResource {

    private static final String ENTITY_NAME = "financialEntry";

    private final FinancialEntryApplicationService financialEntryApplicationService;
    private final FinancialEntryQueryService financialEntryQueryService;

    @GetMapping("/summary")
    public ResponseEntity<FinancialSummary> summary() {
        return ResponseEntity.ok(financialEntryQueryService.getSummary());
    }

    @GetMapping("/entries")
    public ResponseEntity<Page<FinancialEntryDetails>> list(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String expenseType,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        LocalDate start = from != null ? from : LocalDate.now().withDayOfMonth(1);
        LocalDate end = to != null
                ? to
                : LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1);
        return ResponseEntity.ok(financialEntryQueryService.list(
                new FinancialEntryFilter(start, end, category, expenseType, type, page, size)));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> categories() {
        return ResponseEntity.ok(financialEntryQueryService.findDistinctCategories());
    }

    @PostMapping("/entries")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateFinancialEntryCommand command) {
        FinancialEntry entry = financialEntryApplicationService.when(command);
        return RestUtils.message().created(ENTITY_NAME, entry);
    }

    @PutMapping("/entries/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateFinancialEntryCommand command) {
        financialEntryApplicationService.when(command.id(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @PatchMapping("/entries/{id}/toggle-paid")
    public ResponseEntity<Void> togglePaid(@PathVariable UUID id) {
        financialEntryApplicationService.when(new ToggleFinancialEntryPaidCommand(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        financialEntryApplicationService.when(new DeleteFinancialEntryCommand(id));
        return RestUtils.message().deleted(ENTITY_NAME, id);
    }
}
