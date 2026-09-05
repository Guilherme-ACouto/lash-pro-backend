package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateClientUseCaseImpl implements CreateClientUseCase {

    private final ClientRepository clientRepository;

    @Override
    public ClientResult execute(CreateClientCommand command) {
        if (clientRepository.existsByPhone(command.phone())) {
            throw new ClientAlreadyExistsException(command.phone());
        }

        Client client = Client.builder()
                .id(UUID.randomUUID())
                .name(command.name())
                .phone(command.phone())
                .email(command.email())
                .birthDate(command.birthDate())
                .notes(command.notes())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return ClientUseCaseMapper.toResult(clientRepository.save(client));
    }
}
