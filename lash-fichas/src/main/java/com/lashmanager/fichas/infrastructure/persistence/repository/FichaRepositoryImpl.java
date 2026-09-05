package com.lashmanager.fichas.infrastructure.persistence.repository;

import com.lashmanager.fichas.domain.model.Ficha;
import com.lashmanager.fichas.domain.port.out.FichaRepository;
import com.lashmanager.fichas.infrastructure.persistence.mapper.FichaMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FichaRepositoryImpl implements FichaRepository {

    private final FichaJpaRepository jpaRepository;
    private final FichaMapper mapper;

    @Override
    public Optional<Ficha> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsByClientId(UUID clientId) {
        return jpaRepository.existsByClientId(clientId);
    }

    @Override
    public Ficha save(Ficha ficha) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(ficha)));
    }
}
