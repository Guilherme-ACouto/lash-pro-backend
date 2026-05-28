package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository {
    Service save(Service service);
    Optional<Service> findById(UUID id);
    Page<Service> findAll(String search, Boolean active, Pageable pageable);
    void deleteById(UUID id);
}
