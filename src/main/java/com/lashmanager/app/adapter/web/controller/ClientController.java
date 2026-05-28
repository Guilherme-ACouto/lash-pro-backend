package com.lashmanager.app.adapter.web.controller;

import com.lashmanager.app.adapter.web.dto.ClientResponse;
import com.lashmanager.app.adapter.web.dto.CreateClientRequest;
import com.lashmanager.app.adapter.web.dto.UpdateClientRequest;
import com.lashmanager.app.domain.port.in.CreateClientUseCase;
import com.lashmanager.app.domain.port.in.DeactivateClientUseCase;
import com.lashmanager.app.domain.port.in.DeleteClientUseCase;
import com.lashmanager.app.domain.port.in.GetClientUseCase;
import com.lashmanager.app.domain.port.in.ListClientsUseCase;
import com.lashmanager.app.domain.port.in.UpdateClientUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final GetClientUseCase getClientUseCase;
    private final ListClientsUseCase listClientsUseCase;
    private final DeactivateClientUseCase deactivateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest request) {
        var result = createClientUseCase.execute(new CreateClientUseCase.CreateClientCommand(
                request.name(), request.phone(), request.email(), request.birthDate(), request.notes()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(result));
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 20, sort = "name") Pageable pageable
    ) {
        return ResponseEntity.ok(listClientsUseCase.execute(search, active, pageable).map(this::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(toResponse(getClientUseCase.execute(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateClientRequest request
    ) {
        var result = updateClientUseCase.execute(id, new UpdateClientUseCase.UpdateClientCommand(
                request.name(), request.phone(), request.email(), request.birthDate(), request.notes()
        ));
        return ResponseEntity.ok(toResponse(result));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "false") boolean force) {
        deactivateClientUseCase.deactivate(id, force);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        deactivateClientUseCase.reactivate(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        deleteClientUseCase.execute(id);
    }

    private ClientResponse toResponse(CreateClientUseCase.ClientResult r) {
        return new ClientResponse(
                r.id().toString(), r.name(), r.phone(), r.email(),
                r.birthDate(), r.notes(), r.active(), r.createdAt()
        );
    }
}
