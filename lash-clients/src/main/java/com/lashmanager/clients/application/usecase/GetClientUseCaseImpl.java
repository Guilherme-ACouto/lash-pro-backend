package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import com.lashmanager.clients.domain.port.in.GetClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetClientUseCaseImpl implements GetClientUseCase {

    private final ClientRepository clientRepository;

    @Override
    public CreateClientUseCase.ClientResult execute(UUID id) {
        return clientRepository.findById(id)
                .map(ClientUseCaseMapper::toResult)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
}
