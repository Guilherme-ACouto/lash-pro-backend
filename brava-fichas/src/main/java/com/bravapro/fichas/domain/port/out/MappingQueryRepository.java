package com.bravapro.fichas.domain.port.out;

import com.bravapro.fichas.domain.model.Mapping;
import com.bravapro.fichas.domain.model.MappingSummary;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Porta de leitura — separada de MappingRepository (escrita). */
public interface MappingQueryRepository {
    Optional<Mapping> findById(UUID id);

    Page<Mapping> findByClientId(UUID clientId, Pageable pageable);

    /** Agregação por cliente (contagem + data do último) — ver MappingSummary. */
    Page<MappingSummary> listSummaries(String search, Pageable pageable);
}
