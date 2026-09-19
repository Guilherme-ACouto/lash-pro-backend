package com.lashmanager.fichas.domain.port.in;

import com.lashmanager.fichas.application.command.CreateMappingCommand;
import com.lashmanager.fichas.application.command.UpdateMappingCommand;
import com.lashmanager.fichas.domain.model.Mapping;

public interface MappingUseCase {

    Mapping create(CreateMappingCommand command);

    Mapping update(Mapping mapping, UpdateMappingCommand command);

    void delete(Mapping mapping);
}
