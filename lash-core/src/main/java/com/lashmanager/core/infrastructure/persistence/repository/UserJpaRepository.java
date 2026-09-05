package com.lashmanager.core.infrastructure.persistence.repository;

import com.lashmanager.core.infrastructure.persistence.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPasswordResetToken(String token);

    Optional<UserEntity> findByActivationKey(String activationKey);

    boolean existsByEmail(String email);
}
