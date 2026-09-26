package com.bravapro.fichas.domain.port.in;

import com.bravapro.fichas.application.command.CreateMappingCommand;
import com.bravapro.fichas.application.command.UpdateMappingCommand;
import com.bravapro.fichas.domain.model.Mapping;

public interface MappingUseCase {

    Mapping create(CreateMappingCommand command);

    Mapping update(Mapping mapping, UpdateMappingCommand command);

    void delete(Mapping mapping);
}
