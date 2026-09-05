package com.lashmanager.fichas.domain.port.in;

import java.time.LocalDate;
import java.util.UUID;

public interface CreateFichaUseCase {

    record CreateFichaCommand(
            UUID clientId,
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

    record FichaResult(
            UUID id,
            UUID clientId,
            String clientName,
            String date,
            String skinType,
            String eyeShape,
            boolean hasAllergies,
            String allergiesDescription,
            boolean hasMedications,
            String medicationsDescription,
            boolean hasSensitivities,
            String sensitivitiesDescription,
            String observations,
            boolean active,
            String createdAt) {}

    FichaResult execute(CreateFichaCommand command);
}
