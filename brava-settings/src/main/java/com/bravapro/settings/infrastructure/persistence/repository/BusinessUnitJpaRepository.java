package com.bravapro.settings.infrastructure.persistence.repository;

import com.bravapro.settings.infrastructure.persistence.entity.BusinessUnitEntity;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessUnitJpaRepository extends JpaRepository<BusinessUnitEntity, UUID> {

    Optional<BusinessUnitEntity> findFirstByMainTrue();
}
