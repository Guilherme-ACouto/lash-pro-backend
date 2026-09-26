package com.bravapro.fichas.domain.port.out;

import com.bravapro.fichas.domain.model.Anamnese;
import com.bravapro.fichas.domain.model.AnamneseSummary;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Porta de leitura — separada de AnamneseRepository (escrita). */
public interface AnamneseQueryRepository {
    Optional<Anamnese> findByClientId(UUID clientId);

    Optional<Anamnese> findByLinkToken(String token);

    /** Junta todos os clientes (mesmo os sem anamnese) — ver AnamneseSummary. */
    Page<AnamneseSummary> listWithFilters(String search, Pageable pageable);
}
