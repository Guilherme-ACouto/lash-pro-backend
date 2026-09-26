package com.bravapro.core.infrastructure.migration;

import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryDependsOnPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Garante que o EntityManagerFactory só sobe depois do {@link UpdateMigration} — mesmo mecanismo
 * que o Spring Boot usa para o Flyway/Liquibase auto-configurados.
 */
@Component
public class UpdateMigrationDependsOnPostProcessor extends EntityManagerFactoryDependsOnPostProcessor {

    public UpdateMigrationDependsOnPostProcessor() {
        super(UpdateMigration.class);
    }
}
