package com.lashmanager.core.infrastructure.multitenancy;

import com.lashmanager.core.domain.exception.SchemaProvisioningException;
import com.lashmanager.core.domain.port.out.SchemaProvisionerPort;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Provisiona o schema Postgres de um tenant novo: CREATE SCHEMA IF NOT EXISTS + changelog Liquibase
 * completo (master.xml). Roda em conexão JDBC própria, fora do pool/transação gerenciados pelo
 * Spring (ver design.md, RBK-15) — assim uma falha aqui não deixa o rollback do Hibernate preso a
 * uma conexão que já rodou DDL. Idempotente: CREATE SCHEMA IF NOT EXISTS + Liquibase (que rastreia
 * changesets já aplicados via DATABASECHANGELOG) tornam o retry seguro.
 */
@Component
@Slf4j
public class SchemaProvisionerImpl implements SchemaProvisionerPort {

  private static final String MASTER_CHANGELOG = "db/changelog/master.xml";

  private final String url;
  private final String username;
  private final String password;
  private final TenantSchemaNaming tenantSchemaNaming;

  public SchemaProvisionerImpl(
      @Value("${spring.datasource.url}") String url,
      @Value("${spring.datasource.username}") String username,
      @Value("${spring.datasource.password}") String password,
      TenantSchemaNaming tenantSchemaNaming) {
    this.url = url;
    this.username = username;
    this.password = password;
    this.tenantSchemaNaming = tenantSchemaNaming;
  }

  @Override
  public void provision(UUID tenantId) {
    String schemaName = tenantSchemaNaming.schemaNameFor(tenantId);
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      createSchemaIfNotExists(connection, schemaName);
      runChangelog(connection, schemaName);
      log.info("Schema provisionado para tenant {}: {}", tenantId, schemaName);
    } catch (Exception e) {
      throw new SchemaProvisioningException(tenantId, e);
    }
  }

  private void createSchemaIfNotExists(Connection connection, String schemaName)
      throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("CREATE SCHEMA IF NOT EXISTS \"" + schemaName + "\"");
    }
  }

  private void runChangelog(Connection connection, String schemaName) throws Exception {
    Database database =
        DatabaseFactory.getInstance()
            .findCorrectDatabaseImplementation(new JdbcConnection(connection));
    database.setDefaultSchemaName(schemaName);
    try (Liquibase liquibase =
        new Liquibase(MASTER_CHANGELOG, new ClassLoaderResourceAccessor(), database)) {
      liquibase.update();
    }
  }
}
