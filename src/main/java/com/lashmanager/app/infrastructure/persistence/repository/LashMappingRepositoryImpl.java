package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.domain.model.LashMapping;
import com.lashmanager.app.domain.port.in.ListMappingSummaryUseCase;
import com.lashmanager.app.domain.port.out.LashMappingRepository;
import com.lashmanager.app.infrastructure.persistence.mapper.LashMappingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository @RequiredArgsConstructor
public class LashMappingRepositoryImpl implements LashMappingRepository {
    private final LashMappingJpaRepository jpaRepository;
    private final LashMappingMapper mapper;

    @Override public LashMapping save(LashMapping mapping) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(mapping)));
    }

    @Override public Optional<LashMapping> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override public Page<LashMapping> findByClientId(UUID clientId, Pageable pageable) {
        return jpaRepository.findByClientIdOrderByMappingDateDesc(clientId, pageable).map(mapper::toDomain);
    }

    @Override public Page<ListMappingSummaryUseCase.MappingSummaryResult> findAllSummary(String search, Pageable pageable) {
        return jpaRepository.findAllSummary(search, pageable);
    }

    @Override public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}
