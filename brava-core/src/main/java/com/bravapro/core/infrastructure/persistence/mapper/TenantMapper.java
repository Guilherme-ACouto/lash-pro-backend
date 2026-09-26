package com.bravapro.core.infrastructure.persistence.mapper;

import com.bravapro.core.domain.model.Tenant;
import com.bravapro.core.infrastructure.persistence.entity.TenantEntity;
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
                .ownerUserId(entity.getOwnerUserId())
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
                .ownerUserId(domain.getOwnerUserId())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
