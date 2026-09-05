package com.lashmanager.services.domain.port.in;

import java.util.UUID;

public interface DeactivateServiceUseCase {
    void deactivate(UUID id, boolean force);

    void reactivate(UUID id);
}
