package com.lashmanager.clients.domain.port.in;

import com.lashmanager.clients.application.command.CreateClientCommand;
import com.lashmanager.clients.application.command.UpdateClientCommand;
import com.lashmanager.clients.domain.model.Client;

public interface ClientUseCase {

    Client create(CreateClientCommand command);

    void update(Client client, UpdateClientCommand command);

    void delete(Client client);

    void deactivate(Client client, boolean force);

    void reactivate(Client client);
}
