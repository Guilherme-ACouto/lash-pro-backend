package com.bravapro.core.infrastructure.persistence.repository;

import com.bravapro.core.infrastructure.persistence.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPasswordResetToken(String token);

    Optional<UserEntity> findByActivationKey(String activationKey);

    List<UserEntity> findAllByTenantIdOrderByNameAsc(UUID tenantId);

    long countByTenantIdAndAdminTrueAndActiveTrue(UUID tenantId);

    boolean existsByEmail(String email);
}
