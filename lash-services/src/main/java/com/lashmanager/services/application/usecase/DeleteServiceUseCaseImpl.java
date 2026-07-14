package com.lashmanager.services.application.usecase;

import com.lashmanager.services.domain.exception.ServiceNotFoundException;
import com.lashmanager.services.domain.port.in.DeleteServiceUseCase;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import com.lashmanager.core.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteServiceUseCaseImpl implements DeleteServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public void execute(UUID id) {
        if (serviceRepository.findById(id).isEmpty()) {
            throw new ServiceNotFoundException(id);
        }
        if (serviceRepository.hasActiveAppointments(id)) {
            throw new BusinessException("Não é possível excluir: serviço possui agendamentos vinculados.");
        }
        serviceRepository.deleteById(id);
    }
}
