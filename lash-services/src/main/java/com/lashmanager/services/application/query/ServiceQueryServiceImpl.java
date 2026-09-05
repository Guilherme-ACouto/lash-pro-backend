package com.lashmanager.services.application.query;

import com.lashmanager.services.domain.exception.ServiceNotFoundException;
import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.domain.port.in.ServiceQueryService;
import com.lashmanager.services.domain.port.out.ServiceQueryRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceQueryServiceImpl implements ServiceQueryService {

    private final ServiceQueryRepository serviceQueryRepository;

    @Override
    public ServiceOffering getById(UUID id) {
        return serviceQueryRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException(id));
    }

    @Override
    public Page<ServiceOffering> list(String search, Boolean active, Pageable pageable) {
        return serviceQueryRepository.findAll(search, active, pageable);
    }
}
