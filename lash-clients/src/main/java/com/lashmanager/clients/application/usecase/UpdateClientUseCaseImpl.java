package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import com.lashmanager.clients.domain.port.in.UpdateClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateClientUseCaseImpl implements UpdateClientUseCase {

  private final ClientRepository clientRepository;

  @Override
  public CreateClientUseCase.ClientResult execute(UUID id, UpdateClientCommand command) {
    Client existing =
        clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));

    if (clientRepository.existsByPhoneAndIdNot(command.phone(), id)) {
      throw new ClientAlreadyExistsException(command.phone());
    }

    Client updated =
        existing.toBuilder()
            .name(command.name())
            .phone(command.phone())
            .email(command.email())
            .birthDate(command.birthDate())
            .notes(command.notes())
            .updatedAt(LocalDateTime.now())
            .build();

    return ClientUseCaseMapper.toResult(clientRepository.save(updated));
  }
}
