package com.bravapro.core.infrastructure.persistence.mapper;

import com.bravapro.core.domain.model.CommandAuditLog;
import com.bravapro.core.infrastructure.persistence.entity.CommandAuditLogEntity;
import org.springframework.stereotype.Component;

@Component
public class CommandAuditLogMapper {

    public CommandAuditLog toDomain(CommandAuditLogEntity entity) {
        if (entity == null) {
            return null;
        }
        return CommandAuditLog.builder()
                .id(entity.getId())
                .commandClass(entity.getCommandClass())
                .payloadJson(entity.getPayloadJson())
                .userId(entity.getUserId())
                .userName(entity.getUserName())
                .tenantId(entity.getTenantId())
                .executedAt(entity.getExecutedAt())
                .success(entity.isSuccess())
                .build();
    }

    public CommandAuditLogEntity toEntity(CommandAuditLog domain) {
        if (domain == null) {
            return null;
        }
        return CommandAuditLogEntity.builder()
                .id(domain.getId())
                .commandClass(domain.getCommandClass())
                .payloadJson(domain.getPayloadJson())
                .userId(domain.getUserId())
                .userName(domain.getUserName())
                .tenantId(domain.getTenantId())
                .executedAt(domain.getExecutedAt())
                .success(domain.isSuccess())
                .build();
    }
}
