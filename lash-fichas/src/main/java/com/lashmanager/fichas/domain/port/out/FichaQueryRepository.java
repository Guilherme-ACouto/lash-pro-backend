package com.lashmanager.fichas.domain.port.out;

import com.lashmanager.fichas.domain.model.Ficha;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Porta de leitura — separada de FichaRepository (escrita) conforme RBK-27. */
public interface FichaQueryRepository {
    Optional<Ficha> findById(UUID id);

    Optional<Ficha> findByClientId(UUID clientId);

    Page<Ficha> listWithFilters(String search, Pageable pageable);
}
