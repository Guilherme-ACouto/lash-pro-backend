package com.bravapro.clients.infrastructure.persistence.repository;

import com.bravapro.clients.domain.model.Client;
import com.bravapro.clients.domain.port.out.ClientQueryRepository;
import com.bravapro.clients.infrastructure.persistence.mapper.ClientMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClientQueryRepositoryImpl implements ClientQueryRepository {

    private final ClientJpaRepository jpaRepository;
    private final ClientMapper mapper;

    @Override
    public Optional<Client> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Client> findAll(String search, Boolean active, Pageable pageable) {
        return jpaRepository.findAllFiltered(search, active, pageable).map(mapper::toDomain);
    }
}
