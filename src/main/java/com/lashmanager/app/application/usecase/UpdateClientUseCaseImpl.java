package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.port.in.CreateClientUseCase;
import com.lashmanager.app.domain.port.in.UpdateClientUseCase;
import com.lashmanager.app.domain.port.out.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateClientUseCaseImpl implements UpdateClientUseCase {

    private final ClientRepository clientRepository;

    @Override
    public CreateClientUseCase.ClientResult execute(UUID id, UpdateClientCommand command) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        if (clientRepository.existsByPhoneAndIdNot(command.phone(), id)) {
            throw new ClientAlreadyExistsException(command.phone());
        }

        Client updated = Client.builder()
                .id(existing.getId())
                .name(command.name())
                .phone(command.phone())
                .email(command.email())
                .birthDate(command.birthDate())
                .notes(command.notes())
                .active(existing.isActive())
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        return ClientUseCaseMapper.toResult(clientRepository.save(updated));
    }
}
