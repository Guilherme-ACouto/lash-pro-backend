package com.bravapro.core.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "tenant_invite", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantInviteEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "admin", nullable = false)
    private boolean admin;

    @Column(name = "professional", nullable = false)
    private boolean professional;

    /** Chaves de permissão separadas por vírgula. */
    @Column(name = "permissions", columnDefinition = "TEXT")
    private String permissions;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "invited_by")
    private UUID invitedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
