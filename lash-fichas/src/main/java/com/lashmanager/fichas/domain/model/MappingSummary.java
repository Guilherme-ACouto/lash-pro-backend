package com.lashmanager.fichas.domain.model;

import java.time.LocalDate;
import java.util.UUID;

/** Projeção de leitura agregada — 1 linha por cliente (contagem + data do último mapeamento),
 * não é o agregado {@code Mapping} em si. Mora do lado de {@code MappingQueryService}. */
public record MappingSummary(UUID clientId, String clientName, long mappingCount, LocalDate lastMappingDate) {}
