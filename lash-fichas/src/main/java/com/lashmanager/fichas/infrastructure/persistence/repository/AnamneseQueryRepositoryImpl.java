package com.lashmanager.fichas.infrastructure.persistence.repository;

import com.lashmanager.clients.infrastructure.persistence.entity.ClientEntity;
import com.lashmanager.clients.infrastructure.persistence.repository.ClientJpaRepository;
import com.lashmanager.fichas.domain.model.Anamnese;
import com.lashmanager.fichas.domain.model.AnamneseSummary;
import com.lashmanager.fichas.domain.port.out.AnamneseQueryRepository;
import com.lashmanager.fichas.infrastructure.persistence.entity.AnamneseEntity;
import com.lashmanager.fichas.infrastructure.persistence.mapper.AnamneseMapper;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnamneseQueryRepositoryImpl implements AnamneseQueryRepository {

    private final AnamneseJpaRepository jpaRepository;
    private final ClientJpaRepository clientJpaRepository;
    private final AnamneseMapper mapper;

    @Override
    public Optional<Anamnese> findByClientId(UUID clientId) {
        return jpaRepository.findByClientId(clientId).map(mapper::toDomain);
    }

    @Override
    public Optional<Anamnese> findByLinkToken(String token) {
        return jpaRepository.findByLinkToken(token).map(mapper::toDomain);
    }

    @Override
    public Page<AnamneseSummary> listWithFilters(String search, Pageable pageable) {
        Page<ClientEntity> clients = clientJpaRepository.findAllFiltered(search, null, pageable);
        return clients.map(this::toSummary);
    }

    private AnamneseSummary toSummary(ClientEntity client) {
        Optional<AnamneseEntity> anamnese = jpaRepository.findByClientId(client.getId());
        return new AnamneseSummary(
                client.getId(),
                client.getName(),
                anamnese.isPresent(),
                anamnese.map(AnamneseEntity::getUpdatedAt).orElse(null));
    }
}
