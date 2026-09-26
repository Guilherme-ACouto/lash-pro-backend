package com.bravapro.finance.adapter.web.resource;

import com.bravapro.core.infrastructure.web.RestUtils;
import com.bravapro.finance.application.command.CreateFinancialEntryCommand;
import com.bravapro.finance.application.command.DeleteFinancialEntryCommand;
import com.bravapro.finance.application.command.ToggleFinancialEntryPaidCommand;
import com.bravapro.finance.application.command.UpdateFinancialEntryCommand;
import com.bravapro.finance.application.service.FinancialEntryApplicationService;
import com.bravapro.finance.domain.model.FinancialEntry;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só comando — leitura mora em {@link FinancialQueryResource} (mesma URL base). */
@RestController
@RequestMapping("/api/financial")
@RequiredArgsConstructor
public class FinancialResource {

    private static final String ENTITY_NAME = "financialEntry";

    private final FinancialEntryApplicationService financialEntryApplicationService;

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
