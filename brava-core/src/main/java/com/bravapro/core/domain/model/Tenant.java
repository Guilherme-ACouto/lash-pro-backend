package com.bravapro.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Assinatura. {@code ownerUserId} é a titular (quem criou a conta — equivalente ao contractor da
 * Signature na Pontta): sempre administradora, não pode ser inativada nem excluída.
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
    private UUID id;
    private String name;
    private String schemaName;
    private boolean active;
    private UUID ownerUserId;
    private LocalDateTime createdAt;

    public void rename(String name) {
        this.name = name;
    }

    public boolean isOwner(UUID userId) {
        return ownerUserId != null && ownerUserId.equals(userId);
    }
}
