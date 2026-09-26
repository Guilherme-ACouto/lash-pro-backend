package com.bravapro.services.infrastructure.persistence.repository;

import com.bravapro.services.domain.model.ServiceOffering;
import com.bravapro.services.domain.port.out.ServiceQueryRepository;
import com.bravapro.services.infrastructure.persistence.mapper.ServiceMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ServiceQueryRepositoryImpl implements ServiceQueryRepository {

    private final ServiceJpaRepository jpaRepository;
    private final ServiceMapper mapper;

    @Override
    public Optional<ServiceOffering> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<ServiceOffering> findAll(String search, Boolean active, Pageable pageable) {
        return jpaRepository.findAllFiltered(search, active, pageable).map(mapper::toDomain);
    }
}
