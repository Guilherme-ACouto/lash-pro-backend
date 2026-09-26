package com.bravapro.core.infrastructure.migration;

import com.bravapro.core.infrastructure.multitenancy.SchemaUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Aplica os changelogs de um {@link PathType} num schema. Usa conexão JDBC própria, fora do pool e
 * das transações do Spring — DDL que falhe aqui não deixa conexão do Hikari suja.
 */
@Component
public class LiquibaseLoader {

    private final String url;
    private final String username;
    private final String password;

    public LiquibaseLoader(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection openConnection() throws Exception {
        return DriverManager.getConnection(url, username, password);
    }

    public void update(Connection connection, String schemaName, PathType type, Map<String, String> parameters)
            throws Exception {
        SchemaUtils.executeSearchPath(connection, schemaName);
        Database database =
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        database.setDefaultSchemaName(schemaName);

        try (ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor()) {
            for (String changelog : MigrationsPaths.of(type)) {
                // Sem try-with-resources: Liquibase.close() fecha a conexão, que ainda serve os próximos changelogs.
                Liquibase liquibase = new Liquibase(changelog, resourceAccessor, database);
                parameters.forEach(liquibase::setChangeLogParameter);
                liquibase.update();
            }
        }
    }
}
