package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.port.in.CreateClientUseCase;
import com.lashmanager.app.domain.port.out.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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

        Client saved = clientRepository.save(client);
        return ClientUseCaseMapper.toResult(saved);
    }
}
