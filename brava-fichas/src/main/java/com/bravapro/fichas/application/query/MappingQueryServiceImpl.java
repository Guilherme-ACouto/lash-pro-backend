package com.bravapro.fichas.application.query;

import com.bravapro.fichas.domain.exception.MappingNotFoundException;
import com.bravapro.fichas.domain.model.Mapping;
import com.bravapro.fichas.domain.model.MappingSummary;
import com.bravapro.fichas.domain.port.in.MappingQueryService;
import com.bravapro.fichas.domain.port.out.MappingQueryRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MappingQueryServiceImpl implements MappingQueryService {

    private final MappingQueryRepository mappingQueryRepository;

    @Override
    public Mapping getById(UUID id) {
        return mappingQueryRepository.findById(id).orElseThrow(() -> new MappingNotFoundException(id));
    }

    @Override
    public Page<Mapping> listByClient(UUID clientId, Pageable pageable) {
        return mappingQueryRepository.findByClientId(clientId, pageable);
    }

    @Override
    public Page<MappingSummary> listSummaries(String search, Pageable pageable) {
        String normalizedSearch = search != null ? search : "";
        return mappingQueryRepository.listSummaries(normalizedSearch, pageable);
    }
}
