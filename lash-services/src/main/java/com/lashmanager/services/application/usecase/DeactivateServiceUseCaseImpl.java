package com.lashmanager.services.application.usecase;

import com.lashmanager.services.domain.exception.ServiceNotFoundException;
import com.lashmanager.services.domain.model.Service;
import com.lashmanager.services.domain.port.in.DeactivateServiceUseCase;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import com.lashmanager.core.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class DeactivateServiceUseCaseImpl implements DeactivateServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public void deactivate(UUID id, boolean force) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        if (!force && serviceRepository.hasActiveAppointments(id)) {
            throw new BusinessException("Serviço possui agendamentos futuros. Use force=true para desativar mesmo assim.");
        }

        serviceRepository.save(service.toBuilder()
                .active(false)
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public void reactivate(UUID id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        serviceRepository.save(service.toBuilder()
                .active(true)
                .updatedAt(LocalDateTime.now())
                .build());
    }
}
