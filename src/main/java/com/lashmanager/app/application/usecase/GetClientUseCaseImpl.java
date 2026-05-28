package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.port.in.CreateClientUseCase;
import com.lashmanager.app.domain.port.in.GetClientUseCase;
import com.lashmanager.app.domain.port.out.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
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
