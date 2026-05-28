package com.lashmanager.app.adapter.web.controller;

import com.lashmanager.app.adapter.web.dto.CreateServiceRequest;
import com.lashmanager.app.adapter.web.dto.ServiceResponse;
import com.lashmanager.app.adapter.web.dto.UpdateServiceRequest;
import com.lashmanager.app.domain.port.in.CreateServiceUseCase;
import com.lashmanager.app.domain.port.in.DeactivateServiceUseCase;
import com.lashmanager.app.domain.port.in.DeleteServiceUseCase;
import com.lashmanager.app.domain.port.in.GetServiceUseCase;
import com.lashmanager.app.domain.port.in.ListServicesUseCase;
import com.lashmanager.app.domain.port.in.UpdateServiceUseCase;
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
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final CreateServiceUseCase createServiceUseCase;
    private final UpdateServiceUseCase updateServiceUseCase;
    private final GetServiceUseCase getServiceUseCase;
    private final ListServicesUseCase listServicesUseCase;
    private final DeactivateServiceUseCase deactivateServiceUseCase;
    private final DeleteServiceUseCase deleteServiceUseCase;

    @PostMapping
    public ResponseEntity<ServiceResponse> create(@Valid @RequestBody CreateServiceRequest request) {
        var result = createServiceUseCase.execute(new CreateServiceUseCase.CreateServiceCommand(
                request.name(), request.description(), request.price(), request.durationMinutes()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(result));
    }

    @GetMapping
    public ResponseEntity<Page<ServiceResponse>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 20, sort = "name") Pageable pageable
    ) {
        return ResponseEntity.ok(listServicesUseCase.execute(search, active, pageable).map(this::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(toResponse(getServiceUseCase.execute(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateServiceRequest request
    ) {
        var result = updateServiceUseCase.execute(id, new UpdateServiceUseCase.UpdateServiceCommand(
                request.name(), request.description(), request.price(), request.durationMinutes()
        ));
        return ResponseEntity.ok(toResponse(result));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "false") boolean force) {
        deactivateServiceUseCase.deactivate(id, force);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        deactivateServiceUseCase.reactivate(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        deleteServiceUseCase.execute(id);
    }

    private ServiceResponse toResponse(CreateServiceUseCase.ServiceResult r) {
        return new ServiceResponse(
                r.id().toString(), r.name(), r.description(), r.price(),
                r.durationMinutes(), r.active(), r.createdAt()
        );
    }
}
