package com.lashmanager.core.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ListTenantsUseCase {

    record TenantResult(UUID id, String name, String schemaName, boolean active, LocalDateTime createdAt) {}

    Page<TenantResult> execute(Pageable pageable);
}
