package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.domain.model.Service;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import com.lashmanager.app.infrastructure.persistence.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private final ServiceJpaRepository jpaRepository;
    private final ServiceMapper mapper;

    @Override
    public Service save(Service service) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(service)));
    }

    @Override
    public Optional<Service> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Service> findAll(String search, Boolean active, Pageable pageable) {
        return jpaRepository.search(search, active, pageable).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
