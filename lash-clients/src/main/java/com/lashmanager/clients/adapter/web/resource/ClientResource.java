package com.lashmanager.clients.adapter.web.resource;

import com.lashmanager.clients.application.command.CreateClientCommand;
import com.lashmanager.clients.application.command.DeactivateClientCommand;
import com.lashmanager.clients.application.command.DeleteClientCommand;
import com.lashmanager.clients.application.command.ReactivateClientCommand;
import com.lashmanager.clients.application.command.UpdateClientCommand;
import com.lashmanager.clients.application.service.ClientApplicationService;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.ClientQueryService;
import com.lashmanager.core.infrastructure.web.RestUtils;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientResource {

    private static final String ENTITY_NAME = "client";

    private final ClientApplicationService clientApplicationService;
    private final ClientQueryService clientQueryService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateClientCommand command) {
        Client client = clientApplicationService.when(command);
        return RestUtils.message().created(ENTITY_NAME, client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(clientQueryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Client>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(clientQueryService.list(search, active, pageable));
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
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        clientApplicationService.when(new ReactivateClientCommand(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }
}
