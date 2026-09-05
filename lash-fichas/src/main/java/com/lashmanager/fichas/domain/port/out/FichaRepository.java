package com.lashmanager.fichas.domain.port.out;

import com.lashmanager.fichas.domain.model.Ficha;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de escrita. Leitura (busca por cliente, listagem) foi separada para FichaQueryRepository
 * (RBK-27) — findById continua aqui porque os use cases de escrita (Update, CreateLashMapping)
 * precisam do agregado completo.
 */
public interface FichaRepository {
  Optional<Ficha> findById(UUID id);

  boolean existsByClientId(UUID clientId);

  Ficha save(Ficha ficha);
}
