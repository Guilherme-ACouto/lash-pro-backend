package com.lashmanager.clients.domain.port.in;

import java.util.UUID;

public interface GetClientUseCase {
  CreateClientUseCase.ClientResult execute(UUID id);
}
