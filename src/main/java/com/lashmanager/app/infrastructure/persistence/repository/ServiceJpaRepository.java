package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.infrastructure.persistence.entity.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceJpaRepository extends JpaRepository<ServiceEntity, UUID> {

    @Query("""
            SELECT s FROM ServiceEntity s
            WHERE (:active IS NULL OR s.active = :active)
            AND (LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')))
            ORDER BY s.name ASC
            """)
    Page<ServiceEntity> search(
            @Param("search") String search,
            @Param("active") Boolean active,
            Pageable pageable
    );
}
