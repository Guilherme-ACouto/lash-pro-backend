package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.ServiceNotFoundException;
import com.lashmanager.app.domain.model.Service;
import com.lashmanager.app.domain.port.in.CreateServiceUseCase;
import com.lashmanager.app.domain.port.in.UpdateServiceUseCase;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class UpdateServiceUseCaseImpl implements UpdateServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public CreateServiceUseCase.ServiceResult execute(UUID id, UpdateServiceCommand command) {
        Service existing = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        Service updated = Service.builder()
                .id(existing.getId())
                .name(command.name())
                .description(command.description())
                .price(command.price())
                .durationMinutes(command.durationMinutes())
                .active(existing.isActive())
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        return ServiceUseCaseMapper.toResult(serviceRepository.save(updated));
    }
}
