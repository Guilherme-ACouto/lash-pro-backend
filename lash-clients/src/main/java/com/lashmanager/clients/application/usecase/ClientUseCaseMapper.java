package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase;

public class ClientUseCaseMapper {

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
