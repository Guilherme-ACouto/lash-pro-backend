package com.bravapro.core.infrastructure.security;

import com.bravapro.core.application.usecase.PlatformAdminChecker;
import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.domain.port.out.CollaboratorRepository;
import com.bravapro.core.domain.port.out.CurrentAccess;

import java.util.Set;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Snapshot de acesso da requisição (equivalente ao {@code RequestAccessSnapshot} da Pontta): o
 * principal vem do {@link JwtAuthFilter}, e as permissões finas são lidas do schema do tenant uma
 * vez por requisição, sob demanda. A Pontta cacheia em Redis entre requisições; aqui ainda não há
 * Redis, então o cache vive só dentro da requisição — mudança de permissão vale na chamada seguinte.
 */
@Component
@RequiredArgsConstructor
public class CurrentAccessImpl implements CurrentAccess {

    private static final String PERMISSIONS_ATTRIBUTE = CurrentAccessImpl.class.getName() + ".permissions";

    private final CollaboratorRepository collaboratorRepository;
    private final PlatformAdminChecker platformAdminChecker;

    @Override
    public boolean isAuthenticated() {
        return principal() != null;
    }

    @Override
    public UUID userId() {
        AuthenticatedUser user = principal();
        return user != null ? user.getUserId() : null;
    }

    @Override
    public String email() {
        AuthenticatedUser user = principal();
        return user != null ? user.getEmail() : null;
    }

    @Override
    public String userName() {
        AuthenticatedUser user = principal();
        return user != null ? user.getName() : null;
    }

    @Override
    public UUID tenantId() {
        AuthenticatedUser user = principal();
        return user != null ? user.getActiveTenantId() : null;
    }

    @Override
    public boolean isAdmin() {
        AuthenticatedUser user = principal();
        return user != null && (isSupportSession() || user.isAdmin());
    }

    @Override
    public boolean isPlatformAdmin() {
        return platformAdminChecker.isPlatformAdmin(email());
    }

    @Override
    public boolean isSupportSession() {
        AuthenticatedUser user = principal();
        return user != null && user.isSupportSession() && isPlatformAdmin();
    }

    @Override
    public boolean has(Permission permission) {
        if (!isAuthenticated()) {
            return false;
        }
        return isAdmin() || permissionKeys().contains(permission.key());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> permissionKeys() {
        AuthenticatedUser user = principal();
        if (user == null || isAdmin()) {
            return Set.of();
        }
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Object cached = attributes.getAttribute(PERMISSIONS_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
            if (cached != null) {
                return (Set<String>) cached;
            }
        }
        Set<String> keys = Set.copyOf(collaboratorRepository.findPermissionKeys(user.getActiveTenantId(), user.getUserId()));
        if (attributes != null) {
            attributes.setAttribute(PERMISSIONS_ATTRIBUTE, keys, RequestAttributes.SCOPE_REQUEST);
        }
        return keys;
    }

    private AuthenticatedUser principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser user) {
            return user;
        }
        return null;
    }
}
