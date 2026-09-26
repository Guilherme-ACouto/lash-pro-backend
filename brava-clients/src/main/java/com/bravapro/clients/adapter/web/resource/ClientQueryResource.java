package com.bravapro.clients.adapter.web.resource;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.clients.adapter.web.dto.ClientResponse;
import com.bravapro.clients.domain.port.in.ClientQueryService;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só leitura — comando mora em {@link ClientResource} (mesma URL base). */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientQueryResource implements QueryPermissionAware {

    private final ClientQueryService clientQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.of(Permission.CLIENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ClientResponse.from(clientQueryService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(clientQueryService.list(search, active, pageable).map(ClientResponse::from));
    }
}
