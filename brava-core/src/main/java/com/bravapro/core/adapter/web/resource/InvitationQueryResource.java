package com.bravapro.core.adapter.web.resource;

import com.bravapro.core.domain.model.InvitationDetails;
import com.bravapro.core.domain.port.in.InvitationQueryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Público: não implementa {@code QueryPermissionAware}. Devolve {@link InvitationDetails}
 * (composição convite + nome da assinatura feita em application/query, então tipo de domínio).
 */
@RestController
@RequestMapping("/api/invitation")
@RequiredArgsConstructor
public class InvitationQueryResource {

    private final InvitationQueryService invitationQueryService;

    @GetMapping("/{token}")
    public ResponseEntity<InvitationDetails> getByToken(@PathVariable String token) {
        return ResponseEntity.ok(invitationQueryService.getByToken(token));
    }
}
