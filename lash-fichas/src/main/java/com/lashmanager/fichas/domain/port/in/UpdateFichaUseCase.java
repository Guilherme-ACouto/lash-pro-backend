package com.lashmanager.fichas.domain.port.in;

import java.time.LocalDate;
import java.util.UUID;

public interface UpdateFichaUseCase {

  record UpdateFichaCommand(
      UUID id,
      LocalDate date,
      String skinType,
      String eyeShape,
      boolean hasAllergies,
      String allergiesDescription,
      boolean hasMedications,
      String medicationsDescription,
      boolean hasSensitivities,
      String sensitivitiesDescription,
      String observations) {}

  CreateFichaUseCase.FichaResult execute(UpdateFichaCommand command);
}
