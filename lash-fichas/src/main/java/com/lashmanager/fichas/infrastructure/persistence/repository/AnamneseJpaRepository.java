package com.lashmanager.fichas.infrastructure.persistence.repository;

import com.lashmanager.fichas.infrastructure.persistence.entity.AnamneseEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnamneseJpaRepository extends JpaRepository<AnamneseEntity, UUID> {

    Optional<AnamneseEntity> findByClientId(UUID clientId);

    Optional<AnamneseEntity> findByLinkToken(String linkToken);
}
