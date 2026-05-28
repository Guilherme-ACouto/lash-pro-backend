package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.infrastructure.persistence.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientJpaRepository extends JpaRepository<ClientEntity, UUID> {

    boolean existsByPhone(String phone);

    boolean existsByPhoneAndIdNot(String phone, UUID id);

    @Query("""
            SELECT c FROM ClientEntity c
            WHERE (:active IS NULL OR c.active = :active)
            AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                 c.phone LIKE CONCAT('%', :search, '%'))
            ORDER BY c.name ASC
            """)
    Page<ClientEntity> search(
            @Param("search") String search,
            @Param("active") Boolean active,
            Pageable pageable
    );
}
