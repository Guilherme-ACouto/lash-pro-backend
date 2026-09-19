package com.lashmanager.services.application.service;

import com.lashmanager.services.application.command.CreateServiceCommand;
import com.lashmanager.services.application.command.DeactivateServiceCommand;
import com.lashmanager.services.application.command.DeleteServiceCommand;
import com.lashmanager.services.application.command.ReactivateServiceCommand;
import com.lashmanager.services.application.command.UpdateServiceCommand;
import com.lashmanager.services.domain.exception.ServiceNotFoundException;
import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.domain.port.in.ServiceUseCase;
import com.lashmanager.services.domain.port.out.ServiceRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceApplicationService {

    private final ServiceUseCase serviceUseCase;
    private final ServiceRepository serviceRepository;

    public ServiceOffering when(CreateServiceCommand command) {
        return serviceUseCase.create(command);
    }

    public void when(UpdateServiceCommand command) {
        serviceUseCase.update(getOne(command.getId()), command);
    }

    public void when(DeleteServiceCommand command) {
        serviceUseCase.delete(getOne(command.getId()));
    }

    public void when(DeactivateServiceCommand command) {
        serviceUseCase.deactivate(getOne(command.getId()), command.isForce());
    }

    public void when(ReactivateServiceCommand command) {
        serviceUseCase.reactivate(getOne(command.getId()));
    }

    private ServiceOffering getOne(UUID id) {
        return serviceRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException(id));
    }
}
