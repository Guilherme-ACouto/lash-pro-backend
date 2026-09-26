package com.bravapro.clients.adapter.web.resource;

import com.bravapro.clients.application.command.CreateClientCommand;
import com.bravapro.clients.application.command.DeactivateClientCommand;
import com.bravapro.clients.application.command.DeleteClientCommand;
import com.bravapro.clients.application.command.ReactivateClientCommand;
import com.bravapro.clients.application.command.UpdateClientCommand;
import com.bravapro.clients.application.service.ClientApplicationService;
import com.bravapro.clients.domain.model.Client;
import com.bravapro.core.infrastructure.web.RestUtils;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Só comando — leitura mora em {@link ClientQueryResource} (mesma URL base), separação confirmada
 * no código real do Pontta: nenhum Resource de lá mistura {@code @GetMapping} de
 * listagem/busca com {@code @PostMapping}/{@code @PutMapping}/{@code @DeleteMapping} de comando.
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientResource {

    private static final String ENTITY_NAME = "client";

    private final ClientApplicationService clientApplicationService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateClientCommand command) {
        Client client = clientApplicationService.when(command);
        return RestUtils.message().created(ENTITY_NAME, client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateClientCommand command) {
        clientApplicationService.when(command.id(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientApplicationService.when(new DeleteClientCommand(id));
        return RestUtils.message().deleted(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id, @RequestParam(defaultValue = "false") boolean force) {
        clientApplicationService.when(new DeactivateClientCommand(id, force));
        return RestUtils.message().deactivated(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        clientApplicationService.when(new ReactivateClientCommand(id));
        return RestUtils.message().reactivated(ENTITY_NAME, id);
    }
}
