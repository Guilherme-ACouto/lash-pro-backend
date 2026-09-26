package com.bravapro.core.domain.model;

import com.bravapro.core.domain.permission.Permission;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Usuário visto de dentro de uma assinatura (tabela {@code collaborator} no schema do tenant, como
 * o {@code cooperator} da Pontta): as permissões finas e se atende clientes na agenda. Os dados de
 * login (nome, e-mail, senha, admin) continuam no {@link User}, no public.
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Collaborator {
    private UUID userId;
    private boolean professional;

    @Builder.Default
    private Set<Permission> permissions = EnumSet.noneOf(Permission.class);

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Collaborator of(UUID userId, boolean professional, Set<Permission> permissions) {
        LocalDateTime now = LocalDateTime.now();
        return Collaborator.builder()
                .userId(userId)
                .professional(professional)
                .permissions(copyOf(permissions))
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void update(boolean professional, Set<Permission> permissions) {
        this.professional = professional;
        this.permissions = copyOf(permissions);
        this.updatedAt = LocalDateTime.now();
    }

    private static Set<Permission> copyOf(Set<Permission> permissions) {
        return permissions == null || permissions.isEmpty()
                ? EnumSet.noneOf(Permission.class)
                : EnumSet.copyOf(permissions);
    }
}
