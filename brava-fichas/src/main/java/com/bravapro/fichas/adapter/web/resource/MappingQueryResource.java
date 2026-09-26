package com.bravapro.fichas.adapter.web.resource;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.fichas.adapter.web.dto.MappingResponse;
import com.bravapro.fichas.domain.model.MappingSummary;
import com.bravapro.fichas.domain.port.in.MappingQueryService;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só leitura — comando mora em {@link MappingResource}. */
@RestController
@RequestMapping("/api/mappings")
@RequiredArgsConstructor
public class MappingQueryResource implements QueryPermissionAware {

    private final MappingQueryService mappingQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.of(Permission.RECORD);
    }

    @GetMapping
    public ResponseEntity<Page<MappingSummary>> list(
            @RequestParam(required = false) String search, Pageable pageable) {
        return ResponseEntity.ok(mappingQueryService.listSummaries(search, pageable));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<MappingResponse>> listByClient(@PathVariable UUID clientId, Pageable pageable) {
        return ResponseEntity.ok(mappingQueryService.listByClient(clientId, pageable).map(MappingResponse::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MappingResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(MappingResponse.from(mappingQueryService.getById(id)));
    }
}
