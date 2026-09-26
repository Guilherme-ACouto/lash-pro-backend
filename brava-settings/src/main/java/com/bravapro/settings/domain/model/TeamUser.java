package com.bravapro.settings.domain.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Usuário da assinatura como a tela de Configurações vê: composição do {@code User} (public) com o
 * {@code Collaborator} (schema do tenant) e a titularidade — montada em application/query, por
 * isso tipo de domínio e não Response. Nunca carrega senha.
 */
public record TeamUser(
        UUID id,
        String name,
        String email,
        boolean admin,
        boolean active,
        boolean accountOwner,
        boolean professional,
        Set<String> permissions,
        LocalDateTime lastLoginAt,
        LocalDateTime createdAt) {}
