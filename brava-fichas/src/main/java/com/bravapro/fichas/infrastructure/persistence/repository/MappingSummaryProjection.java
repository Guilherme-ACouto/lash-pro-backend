package com.bravapro.fichas.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.util.UUID;

/** Projeção Spring Data pra agregação (COUNT/MAX) de {@code MappingJpaRepository.findSummaries}. */
public interface MappingSummaryProjection {
    UUID getClientId();

    String getClientName();

    long getMappingCount();

    LocalDate getLastMappingDate();
}
