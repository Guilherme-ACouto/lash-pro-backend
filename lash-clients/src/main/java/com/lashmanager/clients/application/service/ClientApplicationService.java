package com.lashmanager.clients.application.service;

import com.lashmanager.clients.application.command.CreateClientCommand;
import com.lashmanager.clients.application.command.DeactivateClientCommand;
import com.lashmanager.clients.application.command.DeleteClientCommand;
import com.lashmanager.clients.application.command.ReactivateClientCommand;
import com.lashmanager.clients.application.command.UpdateClientCommand;
import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.ClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Único ponto de entrada de escrita do agregado Client — um {@code when()} sobrecarregado por
 * Command, igual ao padrão real da Pontta (ex.: {@code TransactionAccountApplicationService}), em
 * vez de uma classe por operação.
 */
@Service
@RequiredArgsConstructor
public class ClientApplicationService {

    private final ClientUseCase clientUseCase;
    private final ClientRepository clientRepository;

    public Client when(CreateClientCommand command) {
        return clientUseCase.create(command);
    }

    public void when(UpdateClientCommand command) {
        clientUseCase.update(getOne(command.getId()), command);
    }

    public void when(DeleteClientCommand command) {
        clientUseCase.delete(getOne(command.getId()));
    }

    public void when(DeactivateClientCommand command) {
        clientUseCase.deactivate(getOne(command.getId()), command.isForce());
    }

    public void when(ReactivateClientCommand command) {
        clientUseCase.reactivate(getOne(command.getId()));
    }

    private Client getOne(UUID id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }
}
