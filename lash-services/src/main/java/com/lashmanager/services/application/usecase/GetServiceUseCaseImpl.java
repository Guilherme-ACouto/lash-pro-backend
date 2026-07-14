package com.lashmanager.services.application.usecase;

import com.lashmanager.services.domain.exception.ServiceNotFoundException;
import com.lashmanager.services.domain.port.in.CreateServiceUseCase;
import com.lashmanager.services.domain.port.in.GetServiceUseCase;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetServiceUseCaseImpl implements GetServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public CreateServiceUseCase.ServiceResult execute(UUID id) {
        return serviceRepository.findById(id)
                .map(ServiceUseCaseMapper::toResult)
                .orElseThrow(() -> new ServiceNotFoundException(id));
    }
}
