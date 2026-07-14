package com.lashmanager.services.domain.port.out;

import com.lashmanager.services.domain.model.ServiceOffering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository {
    ServiceOffering save(ServiceOffering service);
    Optional<ServiceOffering> findById(UUID id);
    Page<ServiceOffering> findAll(String search, Boolean active, Pageable pageable);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
    boolean hasActiveAppointments(UUID serviceId);
    void deleteById(UUID id);
}
