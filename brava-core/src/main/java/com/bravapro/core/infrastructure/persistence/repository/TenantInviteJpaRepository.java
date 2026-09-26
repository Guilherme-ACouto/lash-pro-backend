package com.bravapro.core.infrastructure.persistence.repository;

import com.bravapro.core.infrastructure.persistence.entity.TenantInviteEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantInviteJpaRepository extends JpaRepository<TenantInviteEntity, UUID> {

    Optional<TenantInviteEntity> findByToken(String token);

    Optional<TenantInviteEntity> findFirstByTenantIdAndEmailIgnoreCaseAndStatus(UUID tenantId, String email, String status);

    List<TenantInviteEntity> findAllByTenantIdAndStatusOrderByCreatedAtDesc(UUID tenantId, String status);
}
