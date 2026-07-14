package com.lashmanager.core.infrastructure.persistence.repository;

import com.lashmanager.core.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPasswordResetToken(String token);
    boolean existsByEmail(String email);
}
