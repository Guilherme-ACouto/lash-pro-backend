package com.bravapro.core.domain.permission;

import java.util.Arrays;
import java.util.Optional;

/**
 * Permissões finas por módulo e ação, no formato hierárquico da Pontta ({@code DEAL} /
 * {@code DEAL_CREATE("deal.create")}): a raiz de cada módulo é a permissão de leitura ("ver"), e as
 * filhas são as ações de escrita. A chave textual é o que fica gravado no banco
 * ({@code collaborator_permission.permission}) e o que o front recebe — renomear uma constante sem
 * manter a chave invalida as permissões já concedidas.
 *
 * <p>Administradores da assinatura não dependem dessas chaves: têm acesso total (ver
 * {@link com.bravapro.core.domain.port.out.CurrentAccess}). Configurações não têm chave própria —
 * são sempre exclusivas de administrador.
 */
public enum Permission {
    DASHBOARD("dashboard", null),

    APPOINTMENT("appointment", null),
    APPOINTMENT_CREATE("appointment.create", APPOINTMENT),
    APPOINTMENT_UPDATE("appointment.update", APPOINTMENT),
    APPOINTMENT_DELETE("appointment.delete", APPOINTMENT),

    CLIENT("client", null),
    CLIENT_CREATE("client.create", CLIENT),
    CLIENT_UPDATE("client.update", CLIENT),
    CLIENT_DELETE("client.delete", CLIENT),

    SERVICE("service", null),
    SERVICE_CREATE("service.create", SERVICE),
    SERVICE_UPDATE("service.update", SERVICE),
    SERVICE_DELETE("service.delete", SERVICE),

    FINANCIAL("financial", null),
    FINANCIAL_CREATE("financial.create", FINANCIAL),
    FINANCIAL_UPDATE("financial.update", FINANCIAL),
    FINANCIAL_DELETE("financial.delete", FINANCIAL),

    INVENTORY("inventory", null),
    INVENTORY_CREATE("inventory.create", INVENTORY),
    INVENTORY_UPDATE("inventory.update", INVENTORY),
    INVENTORY_DELETE("inventory.delete", INVENTORY),

    RECORD("record", null),
    RECORD_CREATE("record.create", RECORD),
    RECORD_UPDATE("record.update", RECORD),
    RECORD_DELETE("record.delete", RECORD);

    private final String key;
    private final Permission parent;

    Permission(String key, Permission parent) {
        this.key = key;
        this.parent = parent;
    }

    public String key() {
        return key;
    }

    public Permission parent() {
        return parent;
    }

    public static Optional<Permission> fromKey(String key) {
        return Arrays.stream(values()).filter(p -> p.key.equals(key)).findFirst();
    }
}
