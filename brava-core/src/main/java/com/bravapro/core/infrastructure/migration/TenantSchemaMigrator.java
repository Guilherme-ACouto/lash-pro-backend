package com.bravapro.core.infrastructure.migration;

import com.bravapro.core.domain.exception.SchemaProvisioningException;
import com.bravapro.core.infrastructure.multitenancy.SchemaUtils;
import com.bravapro.core.infrastructure.multitenancy.TenantSchemaNaming;

import java.sql.Connection;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Cria (se não existir) e migra o schema de um tenant. Idempotente: CREATE SCHEMA IF NOT EXISTS +
 * DATABASECHANGELOG do Liquibase tornam seguro rodar de novo — usado tanto na ativação da conta
 * quanto a cada boot, para todos os tenants.
 */
@Component
@RequiredArgsConstructor
public class TenantSchemaMigrator {

    public static final String TENANT_ID_PARAMETER = "tenantId";

    private final LiquibaseLoader liquibaseLoader;
    private final TenantSchemaNaming tenantSchemaNaming;

    public void migrate(UUID tenantId) {
        String schemaName = tenantSchemaNaming.schemaNameFor(tenantId);
        try (Connection connection = liquibaseLoader.openConnection()) {
            SchemaUtils.createSchemaIfNotExists(connection, schemaName);
            liquibaseLoader.update(connection, schemaName, PathType.TENANT, Map.of(TENANT_ID_PARAMETER, tenantId.toString()));
        } catch (Exception e) {
            throw new SchemaProvisioningException(tenantId, e);
        }
    }
}
