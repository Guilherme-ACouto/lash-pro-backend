package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.infrastructure.persistence.entity.AnamneseTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface AnamneseTokenJpaRepository extends JpaRepository<AnamneseTokenEntity, UUID> {
    Optional<AnamneseTokenEntity> findByTokenAndUsedFalseAndExpiresAtAfter(String token, LocalDateTime now);

    @Modifying
    @Query("DELETE FROM AnamneseTokenEntity t WHERE t.expiresAt < :now")
    void deleteExpired(@Param("now") LocalDateTime now);
}
