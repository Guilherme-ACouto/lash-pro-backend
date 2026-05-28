package com.lashmanager.app.domain.model;

import lombok.*;
import java.time.*;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class AnamneseToken {
    private UUID id;
    private UUID clientId;
    private String token;
    private LocalDateTime expiresAt;
    private boolean used;
    private LocalDateTime createdAt;
}
