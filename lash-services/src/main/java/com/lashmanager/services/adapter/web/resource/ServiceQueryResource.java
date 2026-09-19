package com.lashmanager.services.adapter.web.resource;

import com.lashmanager.services.adapter.web.dto.ServiceResponse;
import com.lashmanager.services.domain.port.in.ServiceQueryService;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só leitura — comando mora em {@link ServiceResource} (mesma URL base). */
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceQueryResource {

    private final ServiceQueryService serviceQueryService;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ServiceResponse.from(serviceQueryService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<ServiceResponse>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(serviceQueryService.list(search, active, pageable).map(ServiceResponse::from));
    }
}
