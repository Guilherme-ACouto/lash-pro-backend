package com.bravapro.fichas.domain.port.in;

import com.bravapro.fichas.domain.model.Anamnese;
import com.bravapro.fichas.domain.model.AnamneseSummary;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnamneseQueryService {

    Anamnese getById(UUID clientId);

    Page<AnamneseSummary> list(String search, Pageable pageable);
}
