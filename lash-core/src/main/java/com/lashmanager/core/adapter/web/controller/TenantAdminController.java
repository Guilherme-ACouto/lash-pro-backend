package com.lashmanager.core.adapter.web.controller;

import com.lashmanager.core.adapter.web.dto.TenantResponse;
import com.lashmanager.core.domain.port.in.DeactivateTenantUseCase;
import com.lashmanager.core.domain.port.in.ListTenantsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/tenants")
@RequiredArgsConstructor
public class TenantAdminController {

    private final ListTenantsUseCase listTenantsUseCase;
    private final DeactivateTenantUseCase deactivateTenantUseCase;

    @GetMapping
    public ResponseEntity<Page<TenantResponse>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(listTenantsUseCase.execute(pageable).map(TenantResponse::from));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        deactivateTenantUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
