package com.lashmanager.app.domain.port.in;

import java.util.UUID;

public interface DeleteLashMappingUseCase {
    void execute(UUID id);
}
