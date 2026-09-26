package com.bravapro.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Conta de acesso (schema public). {@code admin} é a camada "grossa" de acesso, igual à role
 * {@code ROLE_ADMIN} da Pontta: administradora da assinatura tem acesso total. Permissões finas
 * de quem não é admin ficam no {@link Collaborator}, no schema do tenant.
 *
 * <p>{@code tokenVersion} vai no JWT: incrementar invalida todos os tokens já emitidos pra esse
 * usuário (encerrar sessões, inativação).
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User implements DomainEntity {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private boolean admin;
    private boolean active;
    private String passwordResetToken;
    private LocalDateTime passwordResetTokenExpiry;
    private UUID tenantId;
    private String activationKey;
    private LocalDateTime activationKeyExpiry;
    private LocalDateTime lastLoginAt;
    private int tokenVersion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(String name, boolean admin) {
        this.name = name;
        this.admin = admin;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.tokenVersion++;
        this.updatedAt = LocalDateTime.now();
    }

    public void reactivate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void invalidateSessions() {
        this.tokenVersion++;
        this.updatedAt = LocalDateTime.now();
    }

    public void registerLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void requestPasswordReset(String token, LocalDateTime expiry) {
        this.passwordResetToken = token;
        this.passwordResetTokenExpiry = expiry;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isPasswordResetTokenValid() {
        return passwordResetToken != null
                && passwordResetTokenExpiry != null
                && passwordResetTokenExpiry.isAfter(LocalDateTime.now());
    }

    /** Troca a senha (já codificada), consome o token de redefinição e derruba sessões antigas. */
    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
        this.passwordResetToken = null;
        this.passwordResetTokenExpiry = null;
        this.tokenVersion++;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean belongsTo(UUID tenantId) {
        return this.tenantId != null && this.tenantId.equals(tenantId);
    }
}
