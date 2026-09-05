package com.lashmanager.services.application.service;

import com.lashmanager.services.application.command.DeactivateServiceCommand;
import com.lashmanager.services.application.command.ReactivateServiceCommand;
import com.lashmanager.services.domain.port.in.DeactivateServiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeactivateServiceApplicationService {

    private final DeactivateServiceUseCase deactivateServiceUseCase;

    public void when(DeactivateServiceCommand command) {
        deactivateServiceUseCase.deactivate(command.getId(), command.isForce());
    }

    public void when(ReactivateServiceCommand command) {
        deactivateServiceUseCase.reactivate(command.getId());
    }
}
