package com.bravapro.fichas.application.query;

import com.bravapro.fichas.domain.exception.AnamneseNotFoundException;
import com.bravapro.fichas.domain.model.Anamnese;
import com.bravapro.fichas.domain.model.AnamneseSummary;
import com.bravapro.fichas.domain.port.in.AnamneseQueryService;
import com.bravapro.fichas.domain.port.out.AnamneseQueryRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnamneseQueryServiceImpl implements AnamneseQueryService {

    private final AnamneseQueryRepository anamneseQueryRepository;

    @Override
    public Anamnese getById(UUID clientId) {
        return anamneseQueryRepository.findByClientId(clientId).orElseThrow(() -> new AnamneseNotFoundException(clientId));
    }

    @Override
    public Page<AnamneseSummary> list(String search, Pageable pageable) {
        String normalizedSearch = search != null ? search : "";
        return anamneseQueryRepository.listWithFilters(normalizedSearch, pageable);
    }
}
