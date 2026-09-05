package com.lashmanager.clients.domain.port.in;

import java.util.UUID;

public interface DeleteClientUseCase {
  void execute(UUID id);
}
