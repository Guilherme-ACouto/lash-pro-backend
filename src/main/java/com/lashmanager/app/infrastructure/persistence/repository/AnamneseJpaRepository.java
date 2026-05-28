package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.infrastructure.persistence.entity.AnamneseEntity;
import com.lashmanager.app.domain.port.in.ListAnamneseSummaryUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface AnamneseJpaRepository extends JpaRepository<AnamneseEntity, UUID> {
    Optional<AnamneseEntity> findByClientId(UUID clientId);
    boolean existsByClientId(UUID clientId);

    @Query("""
        SELECT new com.lashmanager.app.domain.port.in.ListAnamneseSummaryUseCase$AnamneseSummaryResult(
            c.id, c.name,
            CASE WHEN a.id IS NOT NULL THEN true ELSE false END,
            CAST(a.updatedAt AS string)
        )
        FROM ClientEntity c
        LEFT JOIN AnamneseEntity a ON a.clientId = c.id
        WHERE c.active = true
        AND (:search IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')))
        ORDER BY c.name ASC
        """)
    Page<ListAnamneseSummaryUseCase.AnamneseSummaryResult> findAllSummary(
        @Param("search") String search, Pageable pageable);
}
