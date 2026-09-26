package com.bravapro.settings.adapter.web.dto;

import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.domain.permission.Permission;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Convite pendente. {@code token} vai pra tela (só administradores leem isto) pra permitir
 * "copiar link do convite" quando o e-mail não chegar.
 */
public record TeamInviteResponse(
        UUID id,
        String name,
        String email,
        boolean admin,
        boolean professional,
        Set<String> permissions,
        String token,
        LocalDateTime expiresAt,
        boolean expired,
        LocalDateTime createdAt) {

    public static TeamInviteResponse from(TenantInvite invite) {
        return new TeamInviteResponse(
                invite.getId(),
                invite.getName(),
                invite.getEmail(),
                invite.isAdmin(),
                invite.isProfessional(),
                invite.getPermissions().stream().map(Permission::key).collect(Collectors.toSet()),
                invite.getToken(),
                invite.getExpiresAt(),
                invite.isExpired(),
                invite.getCreatedAt());
    }
}
