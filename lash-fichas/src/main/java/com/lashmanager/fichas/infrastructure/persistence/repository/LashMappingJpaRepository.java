package com.lashmanager.fichas.infrastructure.persistence.repository;

import com.lashmanager.fichas.infrastructure.persistence.entity.LashMappingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface LashMappingJpaRepository extends JpaRepository<LashMappingEntity, UUID> {

    @Query("SELECT m FROM LashMappingEntity m WHERE m.ficha.id = :fichaId ORDER BY m.date DESC")
    Page<LashMappingEntity> findByFichaId(@Param("fichaId") UUID fichaId, Pageable pageable);
}
