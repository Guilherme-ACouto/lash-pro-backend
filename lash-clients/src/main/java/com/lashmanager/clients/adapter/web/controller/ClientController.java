package com.lashmanager.clients.adapter.web.controller;

import com.lashmanager.clients.adapter.web.dto.ClientResponse;
import com.lashmanager.clients.adapter.web.dto.CreateClientRequest;
import com.lashmanager.clients.adapter.web.dto.UpdateClientRequest;
import com.lashmanager.clients.application.command.CreateClientCommand;
import com.lashmanager.clients.application.command.DeactivateClientCommand;
import com.lashmanager.clients.application.command.DeleteClientCommand;
import com.lashmanager.clients.application.command.ReactivateClientCommand;
import com.lashmanager.clients.application.command.UpdateClientCommand;
import com.lashmanager.clients.application.service.CreateClientApplicationService;
import com.lashmanager.clients.application.service.DeactivateClientApplicationService;
import com.lashmanager.clients.application.service.DeleteClientApplicationService;
import com.lashmanager.clients.application.service.UpdateClientApplicationService;
import com.lashmanager.clients.domain.port.in.GetClientUseCase;
import com.lashmanager.clients.domain.port.in.ListClientsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final CreateClientApplicationService createClientApplicationService;
    private final UpdateClientApplicationService updateClientApplicationService;
    private final DeleteClientApplicationService deleteClientApplicationService;
    private final DeactivateClientApplicationService deactivateClientApplicationService;
    private final GetClientUseCase getClientUseCase;
    private final ListClientsUseCase listClientsUseCase;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest request) {
        var result = createClientApplicationService.when(new CreateClientCommand(
                request.name(), request.phone(), request.email(), request.birthDate(), request.notes()
        ));
        return ResponseEntity
                .created(URI.create("/api/clients/" + result.id()))
                .body(ClientResponse.from(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ClientResponse.from(getClientUseCase.execute(id)));
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(
                listClientsUseCase.execute(search, active, pageable).map(ClientResponse::from)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateClientRequest request
    ) {
        var result = updateClientApplicationService.when(new UpdateClientCommand(
                id, request.name(), request.phone(), request.email(), request.birthDate(), request.notes()
        ));
        return ResponseEntity.ok(ClientResponse.from(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteClientApplicationService.when(new DeleteClientCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "false") boolean force
    ) {
        deactivateClientApplicationService.when(new DeactivateClientCommand(id, force));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        deactivateClientApplicationService.when(new ReactivateClientCommand(id));
        return ResponseEntity.noContent().build();
    }
}
