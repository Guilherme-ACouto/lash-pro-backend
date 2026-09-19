package com.lashmanager.fichas.domain.port.in;

import com.lashmanager.fichas.domain.model.Anamnese;
import com.lashmanager.fichas.domain.model.AnamneseSummary;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnamneseQueryService {

    Anamnese getById(UUID clientId);

    Page<AnamneseSummary> list(String search, Pageable pageable);
}
