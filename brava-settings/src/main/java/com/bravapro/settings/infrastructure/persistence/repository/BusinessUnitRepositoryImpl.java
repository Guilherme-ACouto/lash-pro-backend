package com.bravapro.settings.infrastructure.persistence.repository;

import com.bravapro.settings.domain.model.BusinessUnit;
import com.bravapro.settings.domain.port.out.BusinessUnitRepository;
import com.bravapro.settings.infrastructure.persistence.mapper.BusinessUnitMapper;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BusinessUnitRepositoryImpl implements BusinessUnitRepository {

    private final BusinessUnitJpaRepository jpaRepository;
    private final BusinessUnitMapper mapper;

    @Override
    public BusinessUnit save(BusinessUnit businessUnit) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(businessUnit)));
    }

    @Override
    public Optional<BusinessUnit> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
