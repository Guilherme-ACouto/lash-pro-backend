package com.lashmanager.fichas.adapter.web.resource;

import com.lashmanager.fichas.adapter.web.dto.AnamneseResponse;
import com.lashmanager.fichas.domain.model.AnamneseSummary;
import com.lashmanager.fichas.domain.port.in.AnamneseQueryService;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só leitura (autenticado) — comando mora em {@link AnamneseResource}. */
@RestController
@RequestMapping("/api/anamnese")
@RequiredArgsConstructor
public class AnamneseQueryResource {

    private final AnamneseQueryService anamneseQueryService;

    @GetMapping("/{clientId}")
    public ResponseEntity<AnamneseResponse> getById(@PathVariable UUID clientId) {
        return ResponseEntity.ok(AnamneseResponse.from(anamneseQueryService.getById(clientId)));
    }

    @GetMapping
    public ResponseEntity<Page<AnamneseSummary>> list(
            @RequestParam(required = false) String search, Pageable pageable) {
        return ResponseEntity.ok(anamneseQueryService.list(search, pageable));
    }
}
