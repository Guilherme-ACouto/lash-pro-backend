package com.lashmanager.services.domain.port.out;

import com.lashmanager.services.domain.model.ServiceOffering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Porta de leitura — separada de ServiceRepository (escrita) conforme RBK-27.
 * Reaproveita o mesmo ServiceJpaRepository/ServiceMapper por baixo.
 */
public interface ServiceQueryRepository {
    Optional<ServiceOffering> findById(UUID id);
    Page<ServiceOffering> findAll(String search, Boolean active, Pageable pageable);
}
