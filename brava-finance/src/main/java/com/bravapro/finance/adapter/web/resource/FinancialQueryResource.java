package com.bravapro.finance.adapter.web.resource;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.finance.domain.model.FinancialEntryDetails;
import com.bravapro.finance.domain.model.FinancialEntryFilter;
import com.bravapro.finance.domain.model.FinancialSummary;
import com.bravapro.finance.domain.port.in.FinancialEntryQueryService;

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
public class FinancialQueryResource implements QueryPermissionAware {

    private final FinancialEntryQueryService financialEntryQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.of(Permission.FINANCIAL);
    }

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
