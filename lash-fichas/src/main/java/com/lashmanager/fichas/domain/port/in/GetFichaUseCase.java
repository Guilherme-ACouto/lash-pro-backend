package com.lashmanager.fichas.domain.port.in;

import java.util.UUID;

public interface GetFichaUseCase {
  CreateFichaUseCase.FichaResult execute(UUID id);

  CreateFichaUseCase.FichaResult executeByClient(UUID clientId);
}
