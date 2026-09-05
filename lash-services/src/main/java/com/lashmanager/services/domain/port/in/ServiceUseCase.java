package com.lashmanager.services.domain.port.in;

import com.lashmanager.services.application.command.CreateServiceCommand;
import com.lashmanager.services.application.command.UpdateServiceCommand;
import com.lashmanager.services.domain.model.ServiceOffering;

public interface ServiceUseCase {

    ServiceOffering create(CreateServiceCommand command);

    void update(ServiceOffering service, UpdateServiceCommand command);

    void delete(ServiceOffering service);

    void deactivate(ServiceOffering service, boolean force);

    void reactivate(ServiceOffering service);
}
