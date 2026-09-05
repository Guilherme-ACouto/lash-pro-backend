package com.lashmanager.clients.application.service;

import com.lashmanager.clients.application.command.CreateClientCommand;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateClientApplicationService {

    private final CreateClientUseCase createClientUseCase;

    public CreateClientUseCase.ClientResult when(CreateClientCommand command) {
        return createClientUseCase.execute(command.toDomainCommand());
    }
}
