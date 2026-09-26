package com.bravapro.fichas.adapter.web.resource;

import com.bravapro.core.infrastructure.web.RestUtils;
import com.bravapro.fichas.application.command.CreateMappingCommand;
import com.bravapro.fichas.application.command.DeleteMappingCommand;
import com.bravapro.fichas.application.command.UpdateMappingCommand;
import com.bravapro.fichas.application.service.MappingApplicationService;
import com.bravapro.fichas.domain.model.Mapping;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só comando — leitura mora em {@link MappingQueryResource}. */
@RestController
@RequestMapping("/api/mappings")
@RequiredArgsConstructor
public class MappingResource {

    private static final String ENTITY_NAME = "mapping";

    private final MappingApplicationService mappingApplicationService;

    @PostMapping("/client/{clientId}")
    public ResponseEntity<Object> create(
            @PathVariable UUID clientId, @Valid @RequestBody CreateMappingCommand command) {
        Mapping mapping = mappingApplicationService.when(command.clientId(clientId));
        return RestUtils.message().created(ENTITY_NAME, mapping);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody UpdateMappingCommand command) {
        Mapping mapping = mappingApplicationService.when(command.id(id));
        return RestUtils.message().updated(ENTITY_NAME, mapping);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        mappingApplicationService.when(new DeleteMappingCommand(id));
        return RestUtils.message().deleted(ENTITY_NAME, id);
    }
}
