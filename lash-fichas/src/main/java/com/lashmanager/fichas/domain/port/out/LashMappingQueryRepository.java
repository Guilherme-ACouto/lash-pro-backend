package com.lashmanager.fichas.domain.port.out;

import com.lashmanager.fichas.domain.model.LashMapping;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Porta de leitura — separada de LashMappingRepository (escrita) conforme RBK-27. */
public interface LashMappingQueryRepository {
  Page<LashMapping> findByFichaId(UUID fichaId, Pageable pageable);
}
