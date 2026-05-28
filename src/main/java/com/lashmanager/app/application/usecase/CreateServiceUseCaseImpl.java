package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.Service;
import com.lashmanager.app.domain.port.in.CreateServiceUseCase;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CreateServiceUseCaseImpl implements CreateServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public ServiceResult execute(CreateServiceCommand command) {
        Service service = Service.builder()
                .id(UUID.randomUUID())
                .name(command.name())
                .description(command.description())
                .price(command.price())
                .durationMinutes(command.durationMinutes())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Service saved = serviceRepository.save(service);
        return ServiceUseCaseMapper.toResult(saved);
    }
}
