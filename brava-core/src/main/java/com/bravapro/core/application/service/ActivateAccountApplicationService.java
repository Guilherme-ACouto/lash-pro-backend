package com.bravapro.core.application.service;

import com.bravapro.core.application.command.ActivateAccountCommand;
import com.bravapro.core.domain.port.in.ActivateAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateAccountApplicationService {

    private final ActivateAccountUseCase activateAccountUseCase;

    public ActivateAccountUseCase.ActivationResult when(ActivateAccountCommand command) {
        return activateAccountUseCase.execute(command.getActivationKey());
    }
}
