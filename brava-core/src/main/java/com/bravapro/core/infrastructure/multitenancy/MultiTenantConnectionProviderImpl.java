package com.bravapro.core.infrastructure.multitenancy;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

/**
 * Troca o search_path da conexão Postgres de acordo com o tenant atual. Uma única conexão física é
 * reaproveitada pelo pool (HikariCP) — o isolamento entre tenants vem inteiramente do search_path,
 * nenhuma tabela de negócio ganha coluna tenant_id (ver design.md).
 */
@Component
@RequiredArgsConstructor
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String> {

    private final DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        resetSearchPath(connection);
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = getAnyConnection();
        setSearchPath(connection, tenantIdentifier);
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        resetSearchPath(connection);
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    private void setSearchPath(Connection connection, String schemaName) throws SQLException {
        SchemaUtils.executeSearchPath(connection, schemaName);
    }

    private void resetSearchPath(Connection connection) throws SQLException {
        SchemaUtils.executeSearchPath(connection, TenantContext.DEFAULT_TENANT);
    }
}
