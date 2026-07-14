package com.lashmanager.fichas.domain.port.out;

import com.lashmanager.fichas.domain.model.LashMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface LashMappingRepository {
    Optional<LashMapping> findById(UUID id);
    LashMapping save(LashMapping mapping);
    void delete(UUID id);
    Page<LashMapping> findByFichaId(UUID fichaId, Pageable pageable);
}
