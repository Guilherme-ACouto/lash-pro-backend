package com.bravapro.core.infrastructure.migration;

import com.bravapro.core.infrastructure.multitenancy.TenantContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Migração no boot, antes do JPA subir (ver {@link UpdateMigrationDependsOnPostProcessor}): migra o
 * public e depois todos os tenants cadastrados em paralelo — mesmo fluxo do UpdateMigration da
 * Pontta. Sem isso, um changeset novo só chegaria aos tenants ativados depois dele. Qualquer falha
 * interrompe o boot.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateMigration implements InitializingBean {

    private static final int MAX_PARALLEL_TENANTS = 4;
    private static final String TENANT_SCHEMA_PREFIX_PARAMETER = "tenantSchemaPrefix";

    private final LiquibaseLoader liquibaseLoader;
    private final TenantSchemaMigrator tenantSchemaMigrator;

    @Value("${app.tenant.schema-prefix:tenant_}")
    private String tenantSchemaPrefix;

    @Override
    public void afterPropertiesSet() {
        execute();
    }

    public void execute() {
        migratePublic();
        List<UUID> tenantIds = listTenantIds();
        migrateTenants(tenantIds);
        log.info("Migração concluída: public + {} tenant(s)", tenantIds.size());
    }

    private void migratePublic() {
        try (Connection connection = liquibaseLoader.openConnection()) {
            liquibaseLoader.update(connection, TenantContext.DEFAULT_TENANT, PathType.PUBLIC,
                    Map.of(TENANT_SCHEMA_PREFIX_PARAMETER, tenantSchemaPrefix));
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao migrar o schema public", e);
        }
    }

    private List<UUID> listTenantIds() {
        List<UUID> ids = new ArrayList<>();
        try (Connection connection = liquibaseLoader.openConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT id FROM public.tenants")) {
            while (resultSet.next()) {
                ids.add(resultSet.getObject("id", UUID.class));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao listar os tenants para migração", e);
        }
        return ids;
    }

    private void migrateTenants(List<UUID> tenantIds) {
        if (tenantIds.isEmpty()) {
            return;
        }
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(tenantIds.size(), MAX_PARALLEL_TENANTS));
        try {
            CompletableFuture<?>[] migrations = tenantIds.stream()
                    .map(tenantId -> CompletableFuture.runAsync(() -> tenantSchemaMigrator.migrate(tenantId), executor))
                    .toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(migrations).join();
        } finally {
            executor.shutdown();
        }
    }
}
