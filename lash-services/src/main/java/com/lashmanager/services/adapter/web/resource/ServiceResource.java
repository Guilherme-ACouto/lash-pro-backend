package com.lashmanager.services.adapter.web.resource;

import com.lashmanager.core.infrastructure.web.RestUtils;
import com.lashmanager.services.application.command.CreateServiceCommand;
import com.lashmanager.services.application.command.DeactivateServiceCommand;
import com.lashmanager.services.application.command.DeleteServiceCommand;
import com.lashmanager.services.application.command.ReactivateServiceCommand;
import com.lashmanager.services.application.command.UpdateServiceCommand;
import com.lashmanager.services.application.service.ServiceApplicationService;
import com.lashmanager.services.domain.model.ServiceOffering;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só comando — leitura mora em {@link ServiceQueryResource} (mesma URL base). */
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceResource {

    private static final String ENTITY_NAME = "service";

    private final ServiceApplicationService serviceApplicationService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateServiceCommand command) {
        ServiceOffering service = serviceApplicationService.when(command);
        return RestUtils.message().created(ENTITY_NAME, service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateServiceCommand command) {
        serviceApplicationService.when(command.id(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        serviceApplicationService.when(new DeleteServiceCommand(id));
        return RestUtils.message().deleted(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id, @RequestParam(defaultValue = "false") boolean force) {
        serviceApplicationService.when(new DeactivateServiceCommand(id, force));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        serviceApplicationService.when(new ReactivateServiceCommand(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }
}
