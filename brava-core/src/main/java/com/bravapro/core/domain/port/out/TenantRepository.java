package com.bravapro.core.domain.port.out;

import com.bravapro.core.domain.model.Tenant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantRepository {
    Tenant save(Tenant tenant);

    Optional<Tenant> findById(UUID id);

    boolean existsBySchemaName(String schemaName);

    Page<Tenant> findAll(Pageable pageable);
}
