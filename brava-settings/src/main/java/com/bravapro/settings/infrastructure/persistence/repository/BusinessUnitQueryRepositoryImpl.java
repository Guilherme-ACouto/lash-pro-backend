package com.bravapro.settings.infrastructure.persistence.repository;

import com.bravapro.settings.domain.model.BusinessUnit;
import com.bravapro.settings.domain.port.out.BusinessUnitQueryRepository;
import com.bravapro.settings.infrastructure.persistence.mapper.BusinessUnitMapper;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BusinessUnitQueryRepositoryImpl implements BusinessUnitQueryRepository {

    private final BusinessUnitJpaRepository jpaRepository;
    private final BusinessUnitMapper mapper;

    @Override
    public Optional<BusinessUnit> findMain() {
        return jpaRepository.findFirstByMainTrue().map(mapper::toDomain);
    }
}
