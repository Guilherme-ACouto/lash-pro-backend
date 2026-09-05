package com.lashmanager.core.adapter.web.dto;

import com.lashmanager.core.domain.port.in.ListTenantsUseCase;
import java.time.LocalDateTime;
import java.util.UUID;

public record TenantResponse(
    UUID id, String name, String schemaName, boolean active, LocalDateTime createdAt) {

  public static TenantResponse from(ListTenantsUseCase.TenantResult result) {
    return new TenantResponse(
        result.id(), result.name(), result.schemaName(), result.active(), result.createdAt());
  }
}
