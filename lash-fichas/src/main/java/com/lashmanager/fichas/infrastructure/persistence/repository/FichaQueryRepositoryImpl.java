package com.lashmanager.fichas.infrastructure.persistence.repository;

import com.lashmanager.fichas.domain.model.Ficha;
import com.lashmanager.fichas.domain.port.out.FichaQueryRepository;
import com.lashmanager.fichas.infrastructure.persistence.mapper.FichaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FichaQueryRepositoryImpl implements FichaQueryRepository {

    private final FichaJpaRepository jpaRepository;
    private final FichaMapper mapper;

    @Override
    public Optional<Ficha> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Ficha> findByClientId(UUID clientId) {
        return jpaRepository.findByClientId(clientId).map(mapper::toDomain);
    }

    @Override
    public Page<Ficha> listWithFilters(String search, Pageable pageable) {
        return jpaRepository.findAllFiltered(search, pageable).map(mapper::toDomain);
    }
}
