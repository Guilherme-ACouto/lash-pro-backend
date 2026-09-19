package com.lashmanager.fichas.domain.port.in;

import com.lashmanager.fichas.domain.model.Mapping;
import com.lashmanager.fichas.domain.model.MappingSummary;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MappingQueryService {

    Mapping getById(UUID id);

    Page<Mapping> listByClient(UUID clientId, Pageable pageable);

    Page<MappingSummary> listSummaries(String search, Pageable pageable);
}
