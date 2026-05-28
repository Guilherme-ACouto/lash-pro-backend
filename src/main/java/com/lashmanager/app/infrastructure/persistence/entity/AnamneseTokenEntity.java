package com.lashmanager.app.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.UUID;

@Entity @Table(name = "anamnese_tokens") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AnamneseTokenEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "client_id", nullable = false) private UUID clientId;
    @Column(name = "token", nullable = false, unique = true) private String token;
    @Column(name = "expires_at", nullable = false) private LocalDateTime expiresAt;
    @Column(name = "used", nullable = false) private boolean used;
    @Column(name = "created_at", nullable = false, updatable = false) private LocalDateTime createdAt;

    @PrePersist void prePersist() { if (createdAt == null) createdAt = LocalDateTime.now(); }
}
