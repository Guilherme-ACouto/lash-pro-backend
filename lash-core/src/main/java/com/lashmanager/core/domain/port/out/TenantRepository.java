package com.lashmanager.core.domain.port.out;

import com.lashmanager.core.domain.model.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository {
    Tenant save(Tenant tenant);
    Optional<Tenant> findById(UUID id);
    boolean existsBySchemaName(String schemaName);
    Page<Tenant> findAll(Pageable pageable);
}
