package com.lashmanager.core.infrastructure.persistence.repository;

import com.lashmanager.core.infrastructure.persistence.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantJpaRepository extends JpaRepository<TenantEntity, UUID> {
    boolean existsBySchemaName(String schemaName);
}
