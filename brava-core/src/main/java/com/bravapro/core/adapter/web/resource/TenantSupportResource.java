package com.bravapro.core.adapter.web.resource;

import com.bravapro.core.application.command.EnterTenantCommand;
import com.bravapro.core.application.service.TenantSupportApplicationService;
import com.bravapro.core.domain.model.SupportSession;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Suporte da plataforma: entrar numa assinatura. Corpo = tokens da sessão de suporte (como o
 * login, não uma entidade — por isso sem {@code RestUtils}).
 */
@RestController
@RequestMapping("/api/admin/tenants")
@RequiredArgsConstructor
public class TenantSupportResource {

    private final TenantSupportApplicationService tenantSupportApplicationService;

    @PostMapping("/{id}/enter")
    public ResponseEntity<SupportSession> enter(@PathVariable UUID id) {
        return ResponseEntity.ok(tenantSupportApplicationService.when(new EnterTenantCommand(id)));
    }
}
