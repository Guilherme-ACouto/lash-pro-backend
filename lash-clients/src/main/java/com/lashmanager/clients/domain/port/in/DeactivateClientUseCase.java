package com.lashmanager.clients.domain.port.in;

import java.util.UUID;

public interface DeactivateClientUseCase {
  void deactivate(UUID id, boolean force);

  void reactivate(UUID id);
}
