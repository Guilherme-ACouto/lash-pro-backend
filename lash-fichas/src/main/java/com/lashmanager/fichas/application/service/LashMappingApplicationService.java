package com.lashmanager.fichas.application.service;

import com.lashmanager.fichas.application.command.CreateLashMappingCommand;
import com.lashmanager.fichas.application.command.DeleteLashMappingCommand;
import com.lashmanager.fichas.application.command.UpdateLashMappingCommand;
import com.lashmanager.fichas.domain.port.in.CreateLashMappingUseCase;
import com.lashmanager.fichas.domain.port.in.DeleteLashMappingUseCase;
import com.lashmanager.fichas.domain.port.in.UpdateLashMappingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LashMappingApplicationService {

    private final CreateLashMappingUseCase createLashMappingUseCase;
    private final UpdateLashMappingUseCase updateLashMappingUseCase;
    private final DeleteLashMappingUseCase deleteLashMappingUseCase;

    public CreateLashMappingUseCase.LashMappingResult when(CreateLashMappingCommand command) {
        return createLashMappingUseCase.execute(command.toDomainCommand());
    }

    public CreateLashMappingUseCase.LashMappingResult when(UpdateLashMappingCommand command) {
        return updateLashMappingUseCase.execute(command.toDomainCommand());
    }

    public void when(DeleteLashMappingCommand command) {
        deleteLashMappingUseCase.execute(command.getId());
    }
}
