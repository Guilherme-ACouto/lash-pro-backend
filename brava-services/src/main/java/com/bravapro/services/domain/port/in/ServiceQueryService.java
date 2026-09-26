package com.bravapro.services.domain.port.in;

import com.bravapro.services.domain.model.ServiceOffering;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceQueryService {

    ServiceOffering getById(UUID id);

    Page<ServiceOffering> list(String search, Boolean active, Pageable pageable);
}
