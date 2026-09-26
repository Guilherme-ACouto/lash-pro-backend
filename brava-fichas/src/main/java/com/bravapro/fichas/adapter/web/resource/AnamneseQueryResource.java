package com.bravapro.fichas.adapter.web.resource;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.fichas.adapter.web.dto.AnamneseResponse;
import com.bravapro.fichas.domain.model.AnamneseSummary;
import com.bravapro.fichas.domain.port.in.AnamneseQueryService;

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
public class AnamneseQueryResource implements QueryPermissionAware {

    private final AnamneseQueryService anamneseQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.of(Permission.RECORD);
    }

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
