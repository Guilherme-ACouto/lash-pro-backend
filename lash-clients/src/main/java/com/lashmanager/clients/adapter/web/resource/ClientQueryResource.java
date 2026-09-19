package com.lashmanager.clients.adapter.web.resource;

import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.ClientQueryService;

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
public class ClientQueryResource {

    private final ClientQueryService clientQueryService;

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(clientQueryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Client>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(clientQueryService.list(search, active, pageable));
    }
}
