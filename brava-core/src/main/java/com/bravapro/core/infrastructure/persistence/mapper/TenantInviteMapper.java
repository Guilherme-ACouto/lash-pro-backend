package com.bravapro.core.infrastructure.persistence.mapper;

import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.persistence.entity.TenantInviteEntity;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TenantInviteMapper {

    public TenantInvite toDomain(TenantInviteEntity entity) {
        return TenantInvite.builder()
                .id(entity.getId())
                .tenantId(entity.getTenantId())
                .email(entity.getEmail())
                .name(entity.getName())
                .admin(entity.isAdmin())
                .professional(entity.isProfessional())
                .permissions(parse(entity.getPermissions()))
                .token(entity.getToken())
                .status(entity.getStatus())
                .expiresAt(entity.getExpiresAt())
                .confirmedAt(entity.getConfirmedAt())
                .invitedBy(entity.getInvitedBy())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public TenantInviteEntity toEntity(TenantInvite domain) {
        return TenantInviteEntity.builder()
                .id(domain.getId())
                .tenantId(domain.getTenantId())
                .email(domain.getEmail())
                .name(domain.getName())
                .admin(domain.isAdmin())
                .professional(domain.isProfessional())
                .permissions(domain.getPermissions().stream().map(Permission::key).collect(Collectors.joining(",")))
                .token(domain.getToken())
                .status(domain.getStatus())
                .expiresAt(domain.getExpiresAt())
                .confirmedAt(domain.getConfirmedAt())
                .invitedBy(domain.getInvitedBy())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    private static Set<Permission> parse(String keys) {
        Set<Permission> permissions = EnumSet.noneOf(Permission.class);
        if (keys != null && !keys.isBlank()) {
            Arrays.stream(keys.split(","))
                    .map(String::trim)
                    .forEach(key -> Permission.fromKey(key).ifPresent(permissions::add));
        }
        return permissions;
    }
}
