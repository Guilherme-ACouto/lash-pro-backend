package com.lashmanager.core.infrastructure.multitenancy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Deriva o nome do schema Postgres de um tenant a partir do seu id — determinístico,
 * sem hífen (limite de identificador do Postgres e alinhado ao design.md).
 */
@Component
public class TenantSchemaNaming {

    private final String schemaPrefix;

    public TenantSchemaNaming(@Value("${app.tenant.schema-prefix:tenant_}") String schemaPrefix) {
        this.schemaPrefix = schemaPrefix;
    }

    public String schemaNameFor(UUID tenantId) {
        return schemaPrefix + tenantId.toString().replace("-", "");
    }
}
