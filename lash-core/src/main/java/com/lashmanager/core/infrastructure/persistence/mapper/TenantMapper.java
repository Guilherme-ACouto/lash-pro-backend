package com.lashmanager.core.infrastructure.persistence.mapper;

import com.lashmanager.core.domain.model.Tenant;
import com.lashmanager.core.infrastructure.persistence.entity.TenantEntity;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper {

  public Tenant toDomain(TenantEntity entity) {
    if (entity == null) {
      return null;
    }
    return Tenant.builder()
        .id(entity.getId())
        .name(entity.getName())
        .schemaName(entity.getSchemaName())
        .active(entity.isActive())
        .createdAt(entity.getCreatedAt())
        .build();
  }

  public TenantEntity toEntity(Tenant domain) {
    if (domain == null) {
      return null;
    }
    return TenantEntity.builder()
        .id(domain.getId())
        .name(domain.getName())
        .schemaName(domain.getSchemaName())
        .active(domain.isActive())
        .createdAt(domain.getCreatedAt())
        .build();
  }
}
