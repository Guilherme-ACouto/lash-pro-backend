package com.bravapro.core.infrastructure.persistence.mapper;

import com.bravapro.core.domain.model.User;
import com.bravapro.core.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .admin(entity.isAdmin())
                .active(entity.isActive())
                .passwordResetToken(entity.getPasswordResetToken())
                .passwordResetTokenExpiry(entity.getPasswordResetTokenExpiry())
                .tenantId(entity.getTenantId())
                .activationKey(entity.getActivationKey())
                .activationKeyExpiry(entity.getActivationKeyExpiry())
                .lastLoginAt(entity.getLastLoginAt())
                .tokenVersion(entity.getTokenVersion())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        return UserEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .admin(domain.isAdmin())
                .active(domain.isActive())
                .passwordResetToken(domain.getPasswordResetToken())
                .passwordResetTokenExpiry(domain.getPasswordResetTokenExpiry())
                .tenantId(domain.getTenantId())
                .activationKey(domain.getActivationKey())
                .activationKeyExpiry(domain.getActivationKeyExpiry())
                .lastLoginAt(domain.getLastLoginAt())
                .tokenVersion(domain.getTokenVersion())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
