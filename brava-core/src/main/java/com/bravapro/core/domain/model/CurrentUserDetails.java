package com.bravapro.core.domain.model;

import java.util.Set;
import java.util.UUID;

/**
 * Quem está logado e o que pode fazer — o front usa pra montar menu, rotas e botões. As
 * permissões vêm por requisição (nunca do token), então recarregar isto reflete na hora qualquer
 * mudança feita pela administradora.
 */
public record CurrentUserDetails(
        UUID id,
        String name,
        String email,
        boolean admin,
        boolean accountOwner,
        boolean platformAdmin,
        boolean supportSession,
        UUID tenantId,
        String tenantName,
        Set<String> permissions) {}
