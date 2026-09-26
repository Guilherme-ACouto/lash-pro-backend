package com.bravapro.core.domain.model;

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
public class CommandAuditLog {
    private UUID id;
    private String commandClass;
    private String payloadJson;
    /** E-mail de quem executou (histórico: sempre foi o nome da autenticação). */
    private String userId;
    private String userName;
    private UUID tenantId;
    private LocalDateTime executedAt;
    private boolean success;
}
