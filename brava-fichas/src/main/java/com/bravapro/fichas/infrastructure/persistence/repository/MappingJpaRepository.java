package com.bravapro.fichas.infrastructure.persistence.repository;

import com.bravapro.fichas.infrastructure.persistence.entity.MappingEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MappingJpaRepository extends JpaRepository<MappingEntity, UUID> {

    Page<MappingEntity> findByClientId(UUID clientId, Pageable pageable);

    @Query(
            value = """
                    SELECT m.clientId AS clientId, m.clientName AS clientName,
                           COUNT(m) AS mappingCount, MAX(m.mappingDate) AS lastMappingDate
                    FROM MappingEntity m
                    WHERE (:search IS NULL OR LOWER(m.clientName) LIKE LOWER(CONCAT('%', :search, '%')))
                    GROUP BY m.clientId, m.clientName
                    """,
            countQuery = """
                    SELECT COUNT(DISTINCT m.clientId) FROM MappingEntity m
                    WHERE (:search IS NULL OR LOWER(m.clientName) LIKE LOWER(CONCAT('%', :search, '%')))
                    """)
    Page<MappingSummaryProjection> findSummaries(@Param("search") String search, Pageable pageable);
}
