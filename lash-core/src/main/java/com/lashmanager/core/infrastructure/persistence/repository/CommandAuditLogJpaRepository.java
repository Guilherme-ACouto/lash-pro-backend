package com.lashmanager.core.infrastructure.persistence.repository;

import com.lashmanager.core.infrastructure.persistence.entity.CommandAuditLogEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandAuditLogJpaRepository extends JpaRepository<CommandAuditLogEntity, UUID> {
  boolean existsByCommandClass(String commandClass);

  Optional<CommandAuditLogEntity> findTopByCommandClassOrderByExecutedAtDesc(String commandClass);
}
