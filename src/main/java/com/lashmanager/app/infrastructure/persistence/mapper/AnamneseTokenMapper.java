package com.lashmanager.app.infrastructure.persistence.mapper;

import com.lashmanager.app.domain.model.AnamneseToken;
import com.lashmanager.app.infrastructure.persistence.entity.AnamneseTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class AnamneseTokenMapper {
    public AnamneseToken toDomain(AnamneseTokenEntity entity) {
        if (entity == null) return null;
        return AnamneseToken.builder()
            .id(entity.getId()).clientId(entity.getClientId())
            .token(entity.getToken()).expiresAt(entity.getExpiresAt())
            .used(entity.isUsed()).createdAt(entity.getCreatedAt()).build();
    }

    public AnamneseTokenEntity toEntity(AnamneseToken domain) {
        if (domain == null) return null;
        return AnamneseTokenEntity.builder()
            .id(domain.getId()).clientId(domain.getClientId())
            .token(domain.getToken()).expiresAt(domain.getExpiresAt())
            .used(domain.isUsed()).createdAt(domain.getCreatedAt()).build();
    }
}
