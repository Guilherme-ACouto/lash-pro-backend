package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.port.in.CreateClientUseCase;

public final class ClientUseCaseMapper {

    private ClientUseCaseMapper() {}

    public static CreateClientUseCase.ClientResult toResult(Client client) {
        return new CreateClientUseCase.ClientResult(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getEmail(),
                client.getBirthDate() != null ? client.getBirthDate().toString() : null,
                client.getNotes(),
                client.isActive(),
                client.getCreatedAt() != null ? client.getCreatedAt().toString() : null
        );
    }
}
