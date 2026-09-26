package com.bravapro.core.infrastructure.multitenancy;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

/**
 * search_path sem fallback para public (padrão Pontta): uma tabela de negócio que não existe no
 * schema do tenant falha alto, em vez de resolver silenciosamente para outro schema. Entidades de
 * plataforma declaram {@code @Table(schema = "public")} explicitamente.
 */
public final class SchemaUtils {

    private static final Pattern VALID_SCHEMA_NAME = Pattern.compile("^[a-zA-Z0-9_]+$");

    private SchemaUtils() {}

    public static void executeSearchPath(Connection connection, String schemaName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SET search_path TO " + quote(schemaName));
        }
    }

    public static void createSchemaIfNotExists(Connection connection, String schemaName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE SCHEMA IF NOT EXISTS " + quote(schemaName));
        }
    }

    private static String quote(String schemaName) {
        if (!VALID_SCHEMA_NAME.matcher(schemaName).matches()) {
            throw new IllegalArgumentException("Nome de schema inválido: " + schemaName);
        }
        return "\"" + schemaName + "\"";
    }
}
