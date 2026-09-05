package com.lashmanager.fichas.application.service;

import com.lashmanager.fichas.application.command.UpdateFichaCommand;
import com.lashmanager.fichas.domain.port.in.CreateFichaUseCase;
import com.lashmanager.fichas.domain.port.in.UpdateFichaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateFichaApplicationService {

    private final UpdateFichaUseCase updateFichaUseCase;

    public CreateFichaUseCase.FichaResult when(UpdateFichaCommand command) {
        return updateFichaUseCase.execute(command.toDomainCommand());
    }
}
