package com.bravapro.core.infrastructure.persistence.repository;

import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.domain.port.out.TenantInviteRepository;
import com.bravapro.core.infrastructure.persistence.mapper.TenantInviteMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TenantInviteRepositoryImpl implements TenantInviteRepository {

    private final TenantInviteJpaRepository jpaRepository;
    private final TenantInviteMapper mapper;

    @Override
    public TenantInvite save(TenantInvite invite) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(invite)));
    }

    @Override
    public Optional<TenantInvite> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<TenantInvite> findByToken(String token) {
        return jpaRepository.findByToken(token).map(mapper::toDomain);
    }

    @Override
    public Optional<TenantInvite> findPendingByTenantAndEmail(UUID tenantId, String email) {
        return jpaRepository
                .findFirstByTenantIdAndEmailIgnoreCaseAndStatus(tenantId, email, TenantInvite.STATUS_PENDING)
                .map(mapper::toDomain);
    }

    @Override
    public List<TenantInvite> findPendingByTenant(UUID tenantId) {
        return jpaRepository
                .findAllByTenantIdAndStatusOrderByCreatedAtDesc(tenantId, TenantInvite.STATUS_PENDING)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
