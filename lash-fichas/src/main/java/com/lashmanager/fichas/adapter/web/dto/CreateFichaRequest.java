package com.lashmanager.fichas.adapter.web.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record CreateFichaRequest(
    @NotNull UUID clientId,
    @NotNull LocalDate date,
    String skinType,
    String eyeShape,
    boolean hasAllergies,
    String allergiesDescription,
    boolean hasMedications,
    String medicationsDescription,
    boolean hasSensitivities,
    String sensitivitiesDescription,
    String observations) {}
