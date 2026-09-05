package com.lashmanager.clients.infrastructure.persistence.repository;

import com.lashmanager.clients.infrastructure.persistence.entity.ClientEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientJpaRepository extends JpaRepository<ClientEntity, UUID> {

  boolean existsByPhone(String phone);

  boolean existsByPhoneAndIdNot(String phone, UUID id);

  @Query(
      """
            SELECT c FROM ClientEntity c
            WHERE (:search IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(c.phone) LIKE LOWER(CONCAT('%', :search, '%')))
            AND (:active IS NULL OR c.active = :active)
            ORDER BY c.name ASC
            """)
  Page<ClientEntity> findAllFiltered(
      @Param("search") String search, @Param("active") Boolean active, Pageable pageable);
}
