package com.lashmanager.fichas.domain.port.out;

import com.lashmanager.fichas.domain.model.LashMapping;

import java.util.Optional;
import java.util.UUID;

/**
 * Porta de escrita. Leitura (listagem por ficha) foi separada para
 * LashMappingQueryRepository (RBK-27).
 */
public interface LashMappingRepository {
    Optional<LashMapping> findById(UUID id);
    LashMapping save(LashMapping mapping);
    void delete(UUID id);
}
