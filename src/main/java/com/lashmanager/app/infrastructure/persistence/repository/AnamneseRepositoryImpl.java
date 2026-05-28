package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.domain.model.Anamnese;
import com.lashmanager.app.domain.port.in.ListAnamneseSummaryUseCase;
import com.lashmanager.app.domain.port.out.AnamneseRepository;
import com.lashmanager.app.infrastructure.persistence.mapper.AnamneseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository @RequiredArgsConstructor
public class AnamneseRepositoryImpl implements AnamneseRepository {
    private final AnamneseJpaRepository jpaRepository;
    private final AnamneseMapper mapper;

    @Override public Anamnese save(Anamnese anamnese) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(anamnese)));
    }

    @Override public Optional<Anamnese> findByClientId(UUID clientId) {
        return jpaRepository.findByClientId(clientId).map(mapper::toDomain);
    }

    @Override public Page<ListAnamneseSummaryUseCase.AnamneseSummaryResult> findAllSummary(String search, Pageable pageable) {
        return jpaRepository.findAllSummary(search, pageable);
    }

    @Override public boolean existsByClientId(UUID clientId) {
        return jpaRepository.existsByClientId(clientId);
    }
}
