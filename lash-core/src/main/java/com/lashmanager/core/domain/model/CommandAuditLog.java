package com.lashmanager.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandAuditLog {
    private UUID id;
    private String commandClass;
    private String payloadJson;
    private String userId;
    private LocalDateTime executedAt;
    private boolean success;
}
