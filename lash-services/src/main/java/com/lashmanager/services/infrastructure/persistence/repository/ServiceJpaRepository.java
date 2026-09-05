package com.lashmanager.services.infrastructure.persistence.repository;

import com.lashmanager.services.infrastructure.persistence.entity.ServiceEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceJpaRepository extends JpaRepository<ServiceEntity, UUID> {

  boolean existsByName(String name);

  boolean existsByNameAndIdNot(String name, UUID id);

  @Query(
      """
            SELECT s FROM ServiceEntity s
            WHERE (:search IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')))
            AND (:active IS NULL OR s.active = :active)
            ORDER BY s.name ASC
            """)
  Page<ServiceEntity> findAllFiltered(
      @Param("search") String search, @Param("active") Boolean active, Pageable pageable);
}
