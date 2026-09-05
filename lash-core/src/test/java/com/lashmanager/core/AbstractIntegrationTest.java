package com.lashmanager.core;

import com.lashmanager.core.domain.port.out.SchemaProvisionerPort;
import com.lashmanager.core.infrastructure.multitenancy.TenantContext;
import com.lashmanager.core.infrastructure.multitenancy.TenantSchemaNaming;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Testes de integração rodam contra um schema de tenant isolado (não o schema public de dev) —
 * prova que o changelog Liquibase provisionado em produção (SchemaProvisionerImpl) também sustenta
 * os módulos de negócio existentes. O schema é criado de forma idempotente a cada teste (CREATE
 * SCHEMA IF NOT EXISTS + changesets já aplicados não rodam de novo) — fica no banco entre
 * execuções, não é dropado ao final; é barato de recriar e simplifica o setup (evita
 * coordenar @BeforeAll/@AfterAll estático com injeção de dependência).
 */
@SpringBootTest(classes = CoreTestApplication.class)
@Transactional
@Rollback
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    protected static final UUID TEST_TENANT_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Autowired
    private SchemaProvisionerPort schemaProvisionerPort;

    @Autowired
    private TenantSchemaNaming tenantSchemaNaming;

    @BeforeEach
    void setUpTenantSchema() {
        schemaProvisionerPort.provision(TEST_TENANT_ID);
        TenantContext.setCurrentTenant(tenantSchemaNaming.schemaNameFor(TEST_TENANT_ID));
    }

    @AfterEach
    void clearTenantSchema() {
        TenantContext.clear();
    }
}
