package com.lashmanager.fichas.infrastructure.persistence.repository;

import com.lashmanager.fichas.infrastructure.persistence.entity.FichaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FichaJpaRepository extends JpaRepository<FichaEntity, UUID> {

    Optional<FichaEntity> findByClientId(UUID clientId);

    boolean existsByClientId(UUID clientId);

    @Query("""
        SELECT f FROM FichaEntity f
        WHERE :search = ''
           OR LOWER(f.clientName) LIKE LOWER(CONCAT('%', :search, '%'))
        ORDER BY f.clientName
    """)
    Page<FichaEntity> findAllFiltered(@Param("search") String search, Pageable pageable);
}
