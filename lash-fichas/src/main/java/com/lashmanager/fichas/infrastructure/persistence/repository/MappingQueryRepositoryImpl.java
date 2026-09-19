package com.lashmanager.fichas.infrastructure.persistence.repository;

import com.lashmanager.fichas.domain.model.Mapping;
import com.lashmanager.fichas.domain.model.MappingSummary;
import com.lashmanager.fichas.domain.port.out.MappingQueryRepository;
import com.lashmanager.fichas.infrastructure.persistence.mapper.MappingMapper;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MappingQueryRepositoryImpl implements MappingQueryRepository {

    private final MappingJpaRepository jpaRepository;
    private final MappingMapper mapper;

    @Override
    public Optional<Mapping> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Mapping> findByClientId(UUID clientId, Pageable pageable) {
        return jpaRepository.findByClientId(clientId, pageable).map(mapper::toDomain);
    }

    @Override
    public Page<MappingSummary> listSummaries(String search, Pageable pageable) {
        return jpaRepository
                .findSummaries(search, pageable)
                .map(p -> new MappingSummary(p.getClientId(), p.getClientName(), p.getMappingCount(), p.getLastMappingDate()));
    }
}
