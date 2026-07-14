package com.lashmanager.services.application.usecase;

import com.lashmanager.services.domain.exception.ServiceAlreadyExistsException;
import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.domain.port.in.CreateServiceUseCase;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateServiceUseCaseImpl implements CreateServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public ServiceResult execute(CreateServiceCommand command) {
        if (serviceRepository.existsByName(command.name())) {
            throw new ServiceAlreadyExistsException(command.name());
        }

        ServiceOffering service = ServiceOffering.builder()
                .id(UUID.randomUUID())
                .name(command.name())
                .description(command.description())
                .price(command.price())
                .durationMinutes(command.durationMinutes())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return ServiceUseCaseMapper.toResult(serviceRepository.save(service));
    }
}
