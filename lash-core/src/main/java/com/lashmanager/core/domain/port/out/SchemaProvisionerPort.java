package com.lashmanager.core.domain.port.out;

import java.util.UUID;

public interface SchemaProvisionerPort {
  /**
   * Cria (se não existir) o schema Postgres do tenant e aplica o changelog Liquibase completo nele.
   * Idempotente: pode ser chamado de novo com o mesmo tenantId sem duplicar nada.
   */
  void provision(UUID tenantId);
}
