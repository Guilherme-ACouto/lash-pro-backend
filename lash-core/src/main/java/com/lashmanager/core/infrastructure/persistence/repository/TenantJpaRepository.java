package com.lashmanager.core.infrastructure.persistence.repository;

import com.lashmanager.core.infrastructure.persistence.entity.TenantEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantJpaRepository extends JpaRepository<TenantEntity, UUID> {
  boolean existsBySchemaName(String schemaName);
}
