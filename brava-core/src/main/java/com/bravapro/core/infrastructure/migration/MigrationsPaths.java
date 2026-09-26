package com.bravapro.core.infrastructure.migration;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

final class MigrationsPaths {

    private MigrationsPaths() {}

    /**
     * Changelogs do tipo pedido, na ordem de execução. Os que não estão no classpath são ignorados:
     * quando um módulo roda a própria suíte de teste isolada, só os changelogs dos módulos dos
     * quais ele depende estão presentes.
     */
    static List<String> of(PathType type) {
        ClassLoader classLoader = MigrationsPaths.class.getClassLoader();
        return Arrays.stream(LiquibasePaths.values())
                .filter(path -> path.type() == type)
                .sorted(Comparator.comparingInt(LiquibasePaths::order))
                .map(LiquibasePaths::path)
                .filter(path -> classLoader.getResource(path) != null)
                .toList();
    }
}
