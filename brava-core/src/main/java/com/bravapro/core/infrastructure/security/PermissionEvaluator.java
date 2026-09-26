package com.bravapro.core.infrastructure.security;

import com.bravapro.core.domain.exception.PermissionDeniedException;
import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.infrastructure.command.CommandPermission;
import com.bravapro.core.infrastructure.web.QueryPermission;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Regra única de permissão pra escrita ({@link CommandPermission}) e leitura
 * ({@link QueryPermission}): administradora passa direto (como no {@code CommandPermissionServiceImpl}
 * e no {@code QueryPermissionEvaluator} da Pontta); os demais precisam da chave.
 */
@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private static final String ADMIN_ONLY_MESSAGE = "Apenas administradores da assinatura podem realizar esta ação";

    private final CurrentAccess currentAccess;

    public void check(CommandPermission required) {
        if (!currentAccess.isAuthenticated()) {
            throw new PermissionDeniedException();
        }
        if (required.admin()) {
            checkAdmin();
            return;
        }
        for (Permission permission : required.value()) {
            checkPermission(permission);
        }
    }

    public void check(QueryPermission required) {
        if (!currentAccess.isAuthenticated()) {
            throw new PermissionDeniedException();
        }
        if (required.isAdmin()) {
            checkAdmin();
        } else if (required.permission() != null) {
            checkPermission(required.permission());
        }
    }

    private void checkAdmin() {
        if (!currentAccess.isAdmin()) {
            throw new PermissionDeniedException(ADMIN_ONLY_MESSAGE);
        }
    }

    private void checkPermission(Permission permission) {
        if (!currentAccess.has(permission)) {
            throw new PermissionDeniedException();
        }
    }
}
