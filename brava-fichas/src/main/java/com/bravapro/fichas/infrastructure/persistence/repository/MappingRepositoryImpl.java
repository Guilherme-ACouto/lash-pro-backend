package com.bravapro.fichas.infrastructure.persistence.repository;

import com.bravapro.fichas.domain.model.Mapping;
import com.bravapro.fichas.domain.port.out.MappingRepository;
import com.bravapro.fichas.infrastructure.persistence.mapper.MappingMapper;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MappingRepositoryImpl implements MappingRepository {

    private final MappingJpaRepository jpaRepository;
    private final MappingMapper mapper;

    @Override
    public Mapping save(Mapping mapping) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(mapping)));
    }

    @Override
    public Optional<Mapping> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}
