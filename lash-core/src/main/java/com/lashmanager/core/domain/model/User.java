package com.lashmanager.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private boolean active;
    private String passwordResetToken;
    private LocalDateTime passwordResetTokenExpiry;
    private UUID tenantId;
    private String activationKey;
    private LocalDateTime activationKeyExpiry;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
