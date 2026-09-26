package com.bravapro.core.domain.model;

import com.bravapro.core.domain.exception.BusinessException;
import com.bravapro.core.domain.permission.Permission;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Convite pra entrar numa assinatura — mesmo mecanismo do {@code signature_invite} da Pontta: um
 * convite por par e-mail + assinatura, token com validade, reenvio renova token e prazo (o link
 * anterior deixa de valer), e o acesso só nasce na confirmação. Extensão do Brava Pro: a Pontta
 * só convida quem já tem conta; aqui a confirmação também cria a conta (nome + senha), então o
 * convite guarda até lá o que a administradora definiu (admin, permissões, profissional).
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TenantInvite implements DomainEntity {

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";

    private UUID id;
    private UUID tenantId;
    private String email;
    private String name;
    private boolean admin;
    private boolean professional;

    @Builder.Default
    private Set<Permission> permissions = EnumSet.noneOf(Permission.class);

    private String token;
    private String status;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    private UUID invitedBy;
    private LocalDateTime createdAt;

    public boolean isPending() {
        return STATUS_PENDING.equals(status);
    }

    public boolean isExpired() {
        return expiresAt == null || expiresAt.isBefore(LocalDateTime.now());
    }

    public void renew(String newToken, LocalDateTime newExpiresAt) {
        assertPending();
        this.token = newToken;
        this.expiresAt = newExpiresAt;
    }

    public void confirm() {
        assertPending();
        this.status = STATUS_CONFIRMED;
        this.confirmedAt = LocalDateTime.now();
    }

    public void assertPending() {
        if (!isPending()) {
            throw new BusinessException("Este convite já foi aceito");
        }
    }
}
