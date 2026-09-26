package com.bravapro.core.domain.port.out;

import com.bravapro.core.domain.model.Collaborator;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Colaboradores ficam no schema do tenant, mas são lidos no filtro de segurança e gravados no
 * aceite do convite (rota pública, sem tenant no contexto) — por isso toda operação recebe o
 * tenant explicitamente em vez de depender do search_path da requisição.
 */
public interface CollaboratorRepository {

    Optional<Collaborator> findByUserId(UUID tenantId, UUID userId);

    List<Collaborator> findAll(UUID tenantId);

    Set<String> findPermissionKeys(UUID tenantId, UUID userId);

    void save(UUID tenantId, Collaborator collaborator);

    void delete(UUID tenantId, UUID userId);
}
