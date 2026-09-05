package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import com.lashmanager.clients.domain.port.in.GetClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientQueryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetClientUseCaseImpl implements GetClientUseCase {

    private final ClientQueryRepository clientQueryRepository;

    @Override
    public CreateClientUseCase.ClientResult execute(UUID id) {
        return clientQueryRepository
                .findById(id)
                .map(ClientUseCaseMapper::toResult)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
}
