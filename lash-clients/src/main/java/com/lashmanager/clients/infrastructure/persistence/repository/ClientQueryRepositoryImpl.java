package com.lashmanager.clients.infrastructure.persistence.repository;

import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.out.ClientQueryRepository;
import com.lashmanager.clients.infrastructure.persistence.mapper.ClientMapper;
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
