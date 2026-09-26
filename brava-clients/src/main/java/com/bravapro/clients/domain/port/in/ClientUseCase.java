package com.bravapro.clients.domain.port.in;

import com.bravapro.clients.application.command.CreateClientCommand;
import com.bravapro.clients.application.command.UpdateClientCommand;
import com.bravapro.clients.domain.model.Client;

public interface ClientUseCase {

    Client create(CreateClientCommand command);

    void update(Client client, UpdateClientCommand command);

    void delete(Client client);

    void deactivate(Client client, boolean force);

    void reactivate(Client client);
}
