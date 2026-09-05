package com.lashmanager.clients.application.service;

import com.lashmanager.clients.application.command.DeactivateClientCommand;
import com.lashmanager.clients.application.command.ReactivateClientCommand;
import com.lashmanager.clients.domain.port.in.DeactivateClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeactivateClientApplicationService {

    private final DeactivateClientUseCase deactivateClientUseCase;

    public void when(DeactivateClientCommand command) {
        deactivateClientUseCase.deactivate(command.getId(), command.isForce());
    }

    public void when(ReactivateClientCommand command) {
        deactivateClientUseCase.reactivate(command.getId());
    }
}
