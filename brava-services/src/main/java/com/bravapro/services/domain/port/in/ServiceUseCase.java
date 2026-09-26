package com.bravapro.services.domain.port.in;

import com.bravapro.services.application.command.CreateServiceCommand;
import com.bravapro.services.application.command.UpdateServiceCommand;
import com.bravapro.services.domain.model.ServiceOffering;

public interface ServiceUseCase {

    ServiceOffering create(CreateServiceCommand command);

    void update(ServiceOffering service, UpdateServiceCommand command);

    void delete(ServiceOffering service);

    void deactivate(ServiceOffering service, boolean force);

    void reactivate(ServiceOffering service);
}
