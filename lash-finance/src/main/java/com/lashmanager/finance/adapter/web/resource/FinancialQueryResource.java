package com.lashmanager.finance.adapter.web.resource;

import com.lashmanager.finance.domain.model.FinancialEntryDetails;
import com.lashmanager.finance.domain.model.FinancialEntryFilter;
import com.lashmanager.finance.domain.model.FinancialSummary;
import com.lashmanager.finance.domain.port.in.FinancialEntryQueryService;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só leitura — comando mora em {@link FinancialResource} (mesma URL base). */
@RestController
@RequestMapping("/api/financial")
@RequiredArgsConstructor
public class FinancialQueryResource {

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
}
