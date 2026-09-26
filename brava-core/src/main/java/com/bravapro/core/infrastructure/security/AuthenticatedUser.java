package com.bravapro.core.infrastructure.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Principal da requisição. {@code activeTenantId} é a assinatura do token — igual à própria
 * ({@code ownTenantId}) no uso normal, diferente só quando a equipe da plataforma entra em outra
 * assinatura em modo suporte.
 */
@Getter
public class AuthenticatedUser implements UserDetails {

    private final UUID userId;
    private final String email;
    private final String name;
    private final String password;
    private final boolean admin;
    private final boolean active;
    private final UUID ownTenantId;
    private final UUID activeTenantId;
    private final int tokenVersion;

    public AuthenticatedUser(
            UUID userId,
            String email,
            String name,
            String password,
            boolean admin,
            boolean active,
            UUID ownTenantId,
            UUID activeTenantId,
            int tokenVersion) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.admin = admin;
        this.active = active;
        this.ownTenantId = ownTenantId;
        this.activeTenantId = activeTenantId;
        this.tokenVersion = tokenVersion;
    }

    public AuthenticatedUser inTenant(UUID tenantId) {
        return new AuthenticatedUser(
                userId, email, name, password, admin, active, ownTenantId, tenantId, tokenVersion);
    }

    public boolean isSupportSession() {
        return activeTenantId != null && !activeTenantId.equals(ownTenantId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (admin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
