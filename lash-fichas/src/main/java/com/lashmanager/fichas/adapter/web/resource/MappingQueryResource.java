package com.lashmanager.fichas.adapter.web.resource;

import com.lashmanager.fichas.adapter.web.dto.MappingResponse;
import com.lashmanager.fichas.domain.model.MappingSummary;
import com.lashmanager.fichas.domain.port.in.MappingQueryService;

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
public class MappingQueryResource {

    private final MappingQueryService mappingQueryService;

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
