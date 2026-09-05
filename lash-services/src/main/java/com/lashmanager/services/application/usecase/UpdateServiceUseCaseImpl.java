package com.lashmanager.services.application.usecase;

import com.lashmanager.services.domain.exception.ServiceAlreadyExistsException;
import com.lashmanager.services.domain.exception.ServiceNotFoundException;
import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.domain.port.in.CreateServiceUseCase;
import com.lashmanager.services.domain.port.in.UpdateServiceUseCase;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateServiceUseCaseImpl implements UpdateServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public CreateServiceUseCase.ServiceResult execute(UUID id, UpdateServiceCommand command) {
        ServiceOffering existing = serviceRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException(id));

        if (serviceRepository.existsByNameAndIdNot(command.name(), id)) {
            throw new ServiceAlreadyExistsException(command.name());
        }

        ServiceOffering updated = existing.toBuilder()
                .name(command.name())
                .description(command.description())
                .price(command.price())
                .durationMinutes(command.durationMinutes())
                .updatedAt(LocalDateTime.now())
                .build();

        return ServiceUseCaseMapper.toResult(serviceRepository.save(updated));
    }
}
