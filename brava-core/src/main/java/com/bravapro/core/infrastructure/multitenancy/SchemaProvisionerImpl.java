package com.bravapro.core.infrastructure.multitenancy;

import com.bravapro.core.domain.port.out.SchemaProvisionerPort;
import com.bravapro.core.infrastructure.migration.TenantSchemaMigrator;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Provisiona o schema de um tenant na ativação da conta (equivalente ao SignatureInitializer da
 * Pontta). A migração em si é do {@link TenantSchemaMigrator}, também usado pelo UpdateMigration
 * no boot para manter os tenants existentes atualizados.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SchemaProvisionerImpl implements SchemaProvisionerPort {

    private final TenantSchemaMigrator tenantSchemaMigrator;

    @Override
    public void provision(UUID tenantId) {
        tenantSchemaMigrator.migrate(tenantId);
        log.info("Schema provisionado para tenant {}", tenantId);
    }
}
