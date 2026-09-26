package com.bravapro.fichas.adapter.web.resource;

import com.bravapro.fichas.application.command.GenerateAnamneseLinkCommand;
import com.bravapro.fichas.application.command.SaveAnamneseCommand;
import com.bravapro.fichas.application.service.AnamneseApplicationService;
import com.bravapro.fichas.domain.model.Anamnese;
import com.bravapro.fichas.domain.model.GeneratedAnamneseLink;
import com.bravapro.core.infrastructure.web.RestUtils;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só comando (autenticado) — leitura mora em {@link AnamneseQueryResource}. */
@RestController
@RequestMapping("/api/anamnese")
@RequiredArgsConstructor
public class AnamneseResource {

    private static final String ENTITY_NAME = "anamnese";

    private final AnamneseApplicationService anamneseApplicationService;

    @PutMapping("/{clientId}")
    public ResponseEntity<Object> save(
            @PathVariable UUID clientId, @Valid @RequestBody SaveAnamneseCommand command) {
        Anamnese anamnese = anamneseApplicationService.when(command.clientId(clientId));
        return RestUtils.message().updated(ENTITY_NAME, anamnese);
    }

    @PostMapping("/{clientId}/link")
    public ResponseEntity<GeneratedAnamneseLink> generateLink(@PathVariable UUID clientId) {
        return ResponseEntity.ok(anamneseApplicationService.when(new GenerateAnamneseLinkCommand(clientId)));
    }
}
