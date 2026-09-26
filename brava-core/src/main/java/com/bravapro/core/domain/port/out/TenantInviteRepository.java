package com.bravapro.core.domain.port.out;

import com.bravapro.core.domain.model.TenantInvite;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TenantInviteRepository {

    TenantInvite save(TenantInvite invite);

    Optional<TenantInvite> findById(UUID id);

    Optional<TenantInvite> findByToken(String token);

    Optional<TenantInvite> findPendingByTenantAndEmail(UUID tenantId, String email);

    List<TenantInvite> findPendingByTenant(UUID tenantId);

    void deleteById(UUID id);
}
