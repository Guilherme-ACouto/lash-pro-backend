package com.lashmanager.core.infrastructure.persistence.mapper;

import com.lashmanager.core.domain.model.CommandAuditLog;
import com.lashmanager.core.infrastructure.persistence.entity.CommandAuditLogEntity;
import org.springframework.stereotype.Component;

@Component
public class CommandAuditLogMapper {

  public CommandAuditLog toDomain(CommandAuditLogEntity entity) {
    if (entity == null) return null;
    return CommandAuditLog.builder()
        .id(entity.getId())
        .commandClass(entity.getCommandClass())
        .payloadJson(entity.getPayloadJson())
        .userId(entity.getUserId())
        .executedAt(entity.getExecutedAt())
        .success(entity.isSuccess())
        .build();
  }

  public CommandAuditLogEntity toEntity(CommandAuditLog domain) {
    if (domain == null) return null;
    return CommandAuditLogEntity.builder()
        .id(domain.getId())
        .commandClass(domain.getCommandClass())
        .payloadJson(domain.getPayloadJson())
        .userId(domain.getUserId())
        .executedAt(domain.getExecutedAt())
        .success(domain.isSuccess())
        .build();
  }
}
