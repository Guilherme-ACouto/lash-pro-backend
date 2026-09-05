package com.lashmanager.services.adapter.web.controller;

import com.lashmanager.services.adapter.web.dto.CreateServiceRequest;
import com.lashmanager.services.adapter.web.dto.ServiceResponse;
import com.lashmanager.services.adapter.web.dto.UpdateServiceRequest;
import com.lashmanager.services.application.command.CreateServiceCommand;
import com.lashmanager.services.application.command.DeactivateServiceCommand;
import com.lashmanager.services.application.command.DeleteServiceCommand;
import com.lashmanager.services.application.command.ReactivateServiceCommand;
import com.lashmanager.services.application.command.UpdateServiceCommand;
import com.lashmanager.services.application.service.CreateServiceApplicationService;
import com.lashmanager.services.application.service.DeactivateServiceApplicationService;
import com.lashmanager.services.application.service.DeleteServiceApplicationService;
import com.lashmanager.services.application.service.UpdateServiceApplicationService;
import com.lashmanager.services.domain.port.in.GetServiceUseCase;
import com.lashmanager.services.domain.port.in.ListServicesUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final CreateServiceApplicationService createServiceApplicationService;
    private final UpdateServiceApplicationService updateServiceApplicationService;
    private final DeleteServiceApplicationService deleteServiceApplicationService;
    private final DeactivateServiceApplicationService deactivateServiceApplicationService;
    private final GetServiceUseCase getServiceUseCase;
    private final ListServicesUseCase listServicesUseCase;

    @PostMapping
    public ResponseEntity<ServiceResponse> create(@Valid @RequestBody CreateServiceRequest request) {
        var result = createServiceApplicationService.when(new CreateServiceCommand(
                request.name(), request.description(), request.price(), request.durationMinutes()));
        return ResponseEntity.created(URI.create("/api/services/" + result.id()))
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
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(
                listServicesUseCase.execute(search, active, pageable).map(ServiceResponse::from));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(
            @PathVariable UUID id, @Valid @RequestBody UpdateServiceRequest request) {
        var result = updateServiceApplicationService.when(new UpdateServiceCommand(
                id, request.name(), request.description(), request.price(), request.durationMinutes()));
        return ResponseEntity.ok(ServiceResponse.from(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteServiceApplicationService.when(new DeleteServiceCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id, @RequestParam(defaultValue = "false") boolean force) {
        deactivateServiceApplicationService.when(new DeactivateServiceCommand(id, force));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        deactivateServiceApplicationService.when(new ReactivateServiceCommand(id));
        return ResponseEntity.noContent().build();
    }
}
