package com.lashmanager.fichas.infrastructure.persistence.repository;

import com.lashmanager.fichas.domain.model.LashMapping;
import com.lashmanager.fichas.domain.port.out.LashMappingQueryRepository;
import com.lashmanager.fichas.infrastructure.persistence.mapper.LashMappingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LashMappingQueryRepositoryImpl implements LashMappingQueryRepository {

    private final LashMappingJpaRepository jpaRepository;
    private final LashMappingMapper mapper;

    @Override
    public Page<LashMapping> findByFichaId(UUID fichaId, Pageable pageable) {
        return jpaRepository.findByFichaId(fichaId, pageable).map(mapper::toDomain);
    }
}
