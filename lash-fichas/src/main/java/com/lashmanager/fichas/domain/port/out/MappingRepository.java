package com.lashmanager.fichas.domain.port.out;

import com.lashmanager.fichas.domain.model.Mapping;
import java.util.Optional;
import java.util.UUID;

/** Porta de escrita. Leitura (histórico por cliente, resumo agregado) foi separada para
 * MappingQueryRepository. */
public interface MappingRepository {
    Mapping save(Mapping mapping);

    Optional<Mapping> findById(UUID id);

    void delete(UUID id);
}
