package com.lashmanager.fichas.domain.port.out;

import com.lashmanager.fichas.domain.model.LashMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Porta de leitura — separada de LashMappingRepository (escrita) conforme RBK-27.
 */
public interface LashMappingQueryRepository {
    Page<LashMapping> findByFichaId(UUID fichaId, Pageable pageable);
}
