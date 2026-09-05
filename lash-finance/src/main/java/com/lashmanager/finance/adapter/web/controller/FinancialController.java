package com.lashmanager.finance.adapter.web.controller;

import com.lashmanager.finance.adapter.web.dto.CreateFinancialEntryRequest;
import com.lashmanager.finance.adapter.web.dto.FinancialEntryResponse;
import com.lashmanager.finance.adapter.web.dto.FinancialSummaryResponse;
import com.lashmanager.finance.adapter.web.dto.UpdateFinancialEntryRequest;
import com.lashmanager.finance.application.command.CreateFinancialEntryCommand;
import com.lashmanager.finance.application.command.DeleteFinancialEntryCommand;
import com.lashmanager.finance.application.command.ToggleFinancialEntryPaidCommand;
import com.lashmanager.finance.application.command.UpdateFinancialEntryCommand;
import com.lashmanager.finance.application.service.CreateFinancialEntryApplicationService;
import com.lashmanager.finance.application.service.DeleteFinancialEntryApplicationService;
import com.lashmanager.finance.application.service.ToggleFinancialEntryPaidApplicationService;
import com.lashmanager.finance.application.service.UpdateFinancialEntryApplicationService;
import com.lashmanager.finance.domain.port.in.GetFinancialSummaryUseCase;
import com.lashmanager.finance.domain.port.in.ListFinancialEntriesUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financial")
@RequiredArgsConstructor
public class FinancialController {

  private final CreateFinancialEntryApplicationService createApplicationService;
  private final UpdateFinancialEntryApplicationService updateApplicationService;
  private final DeleteFinancialEntryApplicationService deleteApplicationService;
  private final ToggleFinancialEntryPaidApplicationService toggleApplicationService;
  private final ListFinancialEntriesUseCase listUseCase;
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
      @RequestParam(defaultValue = "20") int size) {
    LocalDate start = from != null ? from : LocalDate.now().withDayOfMonth(1);
    LocalDate end = to != null ? to : LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1);
    return ResponseEntity.ok(
        listUseCase
            .execute(
                new ListFinancialEntriesUseCase.ListQuery(
                    start, end, category, expenseType, type, page, size))
            .map(FinancialEntryResponse::from));
  }

  @GetMapping("/categories")
  public ResponseEntity<List<String>> categories() {
    return ResponseEntity.ok(listUseCase.findDistinctCategories());
  }

  @PostMapping
  public ResponseEntity<FinancialEntryResponse> create(
      @Valid @RequestBody CreateFinancialEntryRequest req) {
    var result =
        createApplicationService.when(
            new CreateFinancialEntryCommand(
                req.type(),
                req.expenseType(),
                req.description(),
                req.amount(),
                req.dueDate(),
                req.paymentDate(),
                req.category(),
                req.paymentMethod(),
                req.receivedFrom(),
                req.notes()));
    return ResponseEntity.created(URI.create("/api/financial/" + result.id()))
        .body(FinancialEntryResponse.from(result));
  }

  @PutMapping("/{id}")
  public ResponseEntity<FinancialEntryResponse> update(
      @PathVariable UUID id, @Valid @RequestBody UpdateFinancialEntryRequest req) {
    var result =
        updateApplicationService.when(
            new UpdateFinancialEntryCommand(
                id,
                req.type(),
                req.expenseType(),
                req.description(),
                req.amount(),
                req.dueDate(),
                req.paymentDate(),
                req.category(),
                req.paymentMethod(),
                req.receivedFrom(),
                req.notes()));
    return ResponseEntity.ok(FinancialEntryResponse.from(result));
  }

  @PatchMapping("/{id}/toggle-paid")
  public ResponseEntity<FinancialEntryResponse> togglePaid(@PathVariable UUID id) {
    return ResponseEntity.ok(
        FinancialEntryResponse.from(
            toggleApplicationService.when(new ToggleFinancialEntryPaidCommand(id))));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    deleteApplicationService.when(new DeleteFinancialEntryCommand(id));
    return ResponseEntity.noContent().build();
  }
}
