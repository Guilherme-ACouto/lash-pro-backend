package com.lashmanager.services.application.usecase;

import com.lashmanager.core.domain.exception.BusinessException;
import com.lashmanager.services.application.command.CreateServiceCommand;
import com.lashmanager.services.application.command.UpdateServiceCommand;
import com.lashmanager.services.domain.exception.ServiceAlreadyExistsException;
import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.domain.port.in.ServiceUseCase;
import com.lashmanager.services.domain.port.out.ServiceRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceUseCaseImpl implements ServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public ServiceOffering create(CreateServiceCommand command) {
        if (serviceRepository.existsByName(command.getName())) {
            throw new ServiceAlreadyExistsException(command.getName());
        }

        ServiceOffering service = ServiceOffering.builder()
                .id(UUID.randomUUID())
                .name(command.getName())
                .description(command.getDescription())
                .price(command.getPrice())
                .durationMinutes(command.getDurationMinutes())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return serviceRepository.save(service);
    }

    @Override
    public void update(ServiceOffering service, UpdateServiceCommand command) {
        if (serviceRepository.existsByNameAndIdNot(command.getName(), service.getId())) {
            throw new ServiceAlreadyExistsException(command.getName());
        }

        service.update(command);
        serviceRepository.save(service);
    }

    @Override
    public void delete(ServiceOffering service) {
        if (serviceRepository.hasActiveAppointments(service.getId())) {
            throw new BusinessException("Não é possível excluir: serviço possui agendamentos vinculados.");
        }
        serviceRepository.deleteById(service.getId());
    }

    @Override
    public void deactivate(ServiceOffering service, boolean force) {
        if (!force && serviceRepository.hasActiveAppointments(service.getId())) {
            throw new BusinessException(
                    "Serviço possui agendamentos futuros. Use force=true para desativar mesmo assim.");
        }

        service.deactivate();
        serviceRepository.save(service);
    }

    @Override
    public void reactivate(ServiceOffering service) {
        service.reactivate();
        serviceRepository.save(service);
    }
}
