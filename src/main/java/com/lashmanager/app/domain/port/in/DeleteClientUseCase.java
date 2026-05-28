package com.lashmanager.app.domain.port.in;

import java.util.UUID;

public interface DeleteClientUseCase {
    void execute(UUID id);
}
