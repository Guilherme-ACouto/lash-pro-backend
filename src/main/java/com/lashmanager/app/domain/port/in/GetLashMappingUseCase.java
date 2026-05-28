package com.lashmanager.app.domain.port.in;

import java.util.UUID;

public interface GetLashMappingUseCase {
    CreateLashMappingUseCase.MappingResult execute(UUID id);
}
