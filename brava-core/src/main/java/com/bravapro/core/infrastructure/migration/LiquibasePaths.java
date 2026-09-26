package com.bravapro.core.infrastructure.migration;

/**
 * Todos os changelogs do sistema, cada um marcado como {@link PathType#PUBLIC} (plataforma, schema
 * public) ou {@link PathType#TENANT} (negócio, um schema por tenant), com a ordem de execução
 * dentro do seu tipo — mesmo modelo do {@code LiquibasePaths} da Pontta.
 */
public enum LiquibasePaths {

    CORE("db/changelog/core-changelog.xml", PathType.PUBLIC, 1),
    DEV_SEED_PUBLIC("db/changelog/dev/dev-seed-public.xml", PathType.PUBLIC, 2),

    CLIENTS("db/changelog/clients-changelog.xml", PathType.TENANT, 1),
    SERVICES("db/changelog/services-changelog.xml", PathType.TENANT, 2),
    APPOINTMENTS("db/changelog/appointments-changelog.xml", PathType.TENANT, 3),
    FINANCE("db/changelog/finance-changelog.xml", PathType.TENANT, 4),
    STOCK("db/changelog/stock-changelog.xml", PathType.TENANT, 5),
    FICHAS("db/changelog/fichas-changelog.xml", PathType.TENANT, 6),
    DEV_SEED_TENANT("db/changelog/dev/dev-seed-tenant.xml", PathType.TENANT, 7);

    private final String path;
    private final PathType type;
    private final int order;

    LiquibasePaths(String path, PathType type, int order) {
        this.path = path;
        this.type = type;
        this.order = order;
    }

    public String path() {
        return path;
    }

    public PathType type() {
        return type;
    }

    public int order() {
        return order;
    }
}
