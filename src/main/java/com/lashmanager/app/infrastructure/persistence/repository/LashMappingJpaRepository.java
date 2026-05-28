package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.infrastructure.persistence.entity.LashMappingEntity;
import com.lashmanager.app.domain.port.in.ListMappingSummaryUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface LashMappingJpaRepository extends JpaRepository<LashMappingEntity, UUID> {
    Page<LashMappingEntity> findByClientIdOrderByMappingDateDesc(UUID clientId, Pageable pageable);

    @Query("""
        SELECT new com.lashmanager.app.domain.port.in.ListMappingSummaryUseCase$MappingSummaryResult(
            CAST(c.id AS string), c.name,
            COUNT(m.id),
            CAST(MAX(m.mappingDate) AS string)
        )
        FROM ClientEntity c
        LEFT JOIN LashMappingEntity m ON m.clientId = c.id
        WHERE c.active = true
        AND (:search IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')))
        GROUP BY c.id, c.name
        ORDER BY c.name ASC
        """)
    Page<ListMappingSummaryUseCase.MappingSummaryResult> findAllSummary(
        @Param("search") String search, Pageable pageable);
}
