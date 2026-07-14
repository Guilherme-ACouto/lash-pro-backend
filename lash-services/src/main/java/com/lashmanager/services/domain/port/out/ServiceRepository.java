package com.lashmanager.services.domain.port.out;

import com.lashmanager.services.domain.model.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository {
    Service save(Service service);
    Optional<Service> findById(UUID id);
    Page<Service> findAll(String search, Boolean active, Pageable pageable);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
    boolean hasActiveAppointments(UUID serviceId);
    void deleteById(UUID id);
}
