package com.lashmanager.clients.adapter.web.controller;

import com.lashmanager.clients.adapter.web.dto.ClientResponse;
import com.lashmanager.clients.adapter.web.dto.CreateClientRequest;
import com.lashmanager.clients.adapter.web.dto.UpdateClientRequest;
import com.lashmanager.clients.domain.port.in.*;
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

    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final GetClientUseCase getClientUseCase;
    private final ListClientsUseCase listClientsUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final DeactivateClientUseCase deactivateClientUseCase;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest request) {
        var result = createClientUseCase.execute(new CreateClientUseCase.CreateClientCommand(
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
        var result = updateClientUseCase.execute(id, new UpdateClientUseCase.UpdateClientCommand(
                request.name(), request.phone(), request.email(), request.birthDate(), request.notes()
        ));
        return ResponseEntity.ok(ClientResponse.from(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteClientUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "false") boolean force
    ) {
        deactivateClientUseCase.deactivate(id, force);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        deactivateClientUseCase.reactivate(id);
        return ResponseEntity.noContent().build();
    }
}
