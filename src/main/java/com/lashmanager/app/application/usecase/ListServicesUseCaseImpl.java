package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.port.in.CreateServiceUseCase;
import com.lashmanager.app.domain.port.in.ListServicesUseCase;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ListServicesUseCaseImpl implements ListServicesUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public Page<CreateServiceUseCase.ServiceResult> execute(String search, Boolean active, Pageable pageable) {
        String normalizedSearch = (search != null) ? search.trim() : "";
        return serviceRepository.findAll(normalizedSearch, active, pageable)
                .map(ServiceUseCaseMapper::toResult);
    }
}
