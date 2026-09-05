package com.lashmanager.core.infrastructure.persistence.mapper;

import com.lashmanager.core.domain.model.User;
import com.lashmanager.core.domain.model.UserRole;
import com.lashmanager.core.infrastructure.persistence.entity.UserEntity;
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
                .role(UserRole.valueOf(entity.getRole()))
                .active(entity.isActive())
                .passwordResetToken(entity.getPasswordResetToken())
                .passwordResetTokenExpiry(entity.getPasswordResetTokenExpiry())
                .tenantId(entity.getTenantId())
                .activationKey(entity.getActivationKey())
                .activationKeyExpiry(entity.getActivationKeyExpiry())
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
                .role(domain.getRole().name())
                .active(domain.isActive())
                .passwordResetToken(domain.getPasswordResetToken())
                .passwordResetTokenExpiry(domain.getPasswordResetTokenExpiry())
                .tenantId(domain.getTenantId())
                .activationKey(domain.getActivationKey())
                .activationKeyExpiry(domain.getActivationKeyExpiry())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
