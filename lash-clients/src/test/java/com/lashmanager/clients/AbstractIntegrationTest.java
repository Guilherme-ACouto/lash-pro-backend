package com.lashmanager.clients;

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
 * Mesma estratégia de lash-core/AbstractIntegrationTest: roda contra um schema de tenant isolado,
 * provisionado via Liquibase (SchemaProvisionerImpl) — os testes de Clientes passam a validar
 * também o changelog de tenant, não só o schema public de dev.
 */
@SpringBootTest(classes = ClientsTestApplication.class)
@Transactional
@Rollback
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

  protected static final UUID TEST_TENANT_ID =
      UUID.fromString("00000000-0000-0000-0000-000000000001");

  @Autowired private SchemaProvisionerPort schemaProvisionerPort;

  @Autowired private TenantSchemaNaming tenantSchemaNaming;

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
