package com.lashmanager.services.adapter.web.controller;

import com.lashmanager.services.adapter.web.dto.CreateServiceRequest;
import com.lashmanager.services.adapter.web.dto.ServiceResponse;
import com.lashmanager.services.adapter.web.dto.UpdateServiceRequest;
import com.lashmanager.services.domain.port.in.*;
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
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final CreateServiceUseCase createServiceUseCase;
    private final UpdateServiceUseCase updateServiceUseCase;
    private final GetServiceUseCase getServiceUseCase;
    private final ListServicesUseCase listServicesUseCase;
    private final DeleteServiceUseCase deleteServiceUseCase;
    private final DeactivateServiceUseCase deactivateServiceUseCase;

    @PostMapping
    public ResponseEntity<ServiceResponse> create(@Valid @RequestBody CreateServiceRequest request) {
        var result = createServiceUseCase.execute(new CreateServiceUseCase.CreateServiceCommand(
                request.name(), request.description(), request.price(), request.durationMinutes()
        ));
        return ResponseEntity
                .created(URI.create("/api/services/" + result.id()))
                .body(ServiceResponse.from(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ServiceResponse.from(getServiceUseCase.execute(id)));
    }

    @GetMapping
    public ResponseEntity<Page<ServiceResponse>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(
                listServicesUseCase.execute(search, active, pageable).map(ServiceResponse::from)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateServiceRequest request
    ) {
        var result = updateServiceUseCase.execute(id, new UpdateServiceUseCase.UpdateServiceCommand(
                request.name(), request.description(), request.price(), request.durationMinutes()
        ));
        return ResponseEntity.ok(ServiceResponse.from(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteServiceUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "false") boolean force
    ) {
        deactivateServiceUseCase.deactivate(id, force);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        deactivateServiceUseCase.reactivate(id);
        return ResponseEntity.noContent().build();
    }
}
