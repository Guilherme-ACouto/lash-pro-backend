package com.lashmanager.core.application.service;

import com.lashmanager.core.application.command.RegisterCommand;
import com.lashmanager.core.domain.port.in.RegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterApplicationService {

    private final RegisterUseCase registerUseCase;

    public RegisterUseCase.RegisterResult when(RegisterCommand command) {
        return registerUseCase.execute(command.toDomainCommand());
    }
}
