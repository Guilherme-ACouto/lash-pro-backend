package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.port.out.ClientRepository;
import com.lashmanager.app.infrastructure.persistence.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientJpaRepository jpaRepository;
    private final ClientMapper mapper;

    @Override
    public Client save(Client client) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(client)));
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Client> findAll(String search, Boolean active, Pageable pageable) {
        return jpaRepository.search(search, active, pageable).map(mapper::toDomain);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return jpaRepository.existsByPhone(phone);
    }

    @Override
    public boolean existsByPhoneAndIdNot(String phone, UUID id) {
        return jpaRepository.existsByPhoneAndIdNot(phone, id);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
