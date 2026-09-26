package com.bravapro.settings.domain.model;

import com.bravapro.core.domain.exception.BusinessException;
import com.bravapro.core.domain.permission.Permission;

import java.util.EnumSet;
import java.util.Set;

/** Converte as chaves que chegam da tela ({@code "client.create"}) nas permissões do domínio. */
public final class PermissionKeys {

    private PermissionKeys() {}

    public static Set<Permission> parse(Set<String> keys) {
        Set<Permission> permissions = EnumSet.noneOf(Permission.class);
        if (keys == null) {
            return permissions;
        }
        for (String key : keys) {
            permissions.add(Permission.fromKey(key)
                    .orElseThrow(() -> new BusinessException("Permissão desconhecida: " + key)));
        }
        return permissions;
    }
}
