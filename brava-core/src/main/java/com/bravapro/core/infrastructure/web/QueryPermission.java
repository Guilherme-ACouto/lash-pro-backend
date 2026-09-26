package com.bravapro.core.infrastructure.web;

import com.bravapro.core.domain.permission.Permission;

/**
 * Permissão exigida pelos {@code GET} de uma QueryResource — igual ao
 * {@code QueryPermission.of(Permission.X)} que cada QueryResource da Pontta devolve em
 * {@code queryPermission()}. Avaliada pelo {@link QueryPermissionInterceptor}.
 */
public final class QueryPermission {

    private final Permission permission;
    private final boolean admin;

    private QueryPermission(Permission permission, boolean admin) {
        this.permission = permission;
        this.admin = admin;
    }

    public static QueryPermission of(Permission permission) {
        return new QueryPermission(permission, false);
    }

    /** Só administradora da assinatura (Configurações). */
    public static QueryPermission admin() {
        return new QueryPermission(null, true);
    }

    /** Qualquer usuário autenticado da assinatura. */
    public static QueryPermission authenticated() {
        return new QueryPermission(null, false);
    }

    public Permission permission() {
        return permission;
    }

    public boolean isAdmin() {
        return admin;
    }
}
