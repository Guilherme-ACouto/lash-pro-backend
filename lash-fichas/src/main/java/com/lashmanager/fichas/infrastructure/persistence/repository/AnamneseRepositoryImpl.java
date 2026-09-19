package com.lashmanager.fichas.infrastructure.persistence.repository;

import com.lashmanager.fichas.domain.model.Anamnese;
import com.lashmanager.fichas.domain.port.out.AnamneseRepository;
import com.lashmanager.fichas.infrastructure.persistence.mapper.AnamneseMapper;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnamneseRepositoryImpl implements AnamneseRepository {

    private final AnamneseJpaRepository jpaRepository;
    private final AnamneseMapper mapper;

    @Override
    public Anamnese save(Anamnese anamnese) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(anamnese)));
    }

    @Override
    public Optional<Anamnese> findByClientId(UUID clientId) {
        return jpaRepository.findByClientId(clientId).map(mapper::toDomain);
    }

    @Override
    public Optional<Anamnese> findByLinkToken(String token) {
        return jpaRepository.findByLinkToken(token).map(mapper::toDomain);
    }
}
