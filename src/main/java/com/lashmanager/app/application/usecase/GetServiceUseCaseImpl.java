package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.ServiceNotFoundException;
import com.lashmanager.app.domain.port.in.CreateServiceUseCase;
import com.lashmanager.app.domain.port.in.GetServiceUseCase;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@org.springframework.stereotype.Service
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
