package com.lashmanager.clients.domain.port.in;

import java.time.LocalDate;
import java.util.UUID;

public interface UpdateClientUseCase {

    record UpdateClientCommand(
            String name,
            String phone,
            String email,
            LocalDate birthDate,
            String notes
    ) {}

    CreateClientUseCase.ClientResult execute(UUID id, UpdateClientCommand command);
}
