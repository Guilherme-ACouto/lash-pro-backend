package com.lashmanager.services.domain.port.in;

import java.util.UUID;

public interface DeleteServiceUseCase {
    void execute(UUID id);
}
