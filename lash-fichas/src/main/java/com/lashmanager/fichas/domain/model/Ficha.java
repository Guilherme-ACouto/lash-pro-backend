package com.lashmanager.fichas.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class Ficha {

    private UUID id;
    private UUID clientId;
    private String clientName;

    private LocalDate date;

    private String skinType;
    private String eyeShape;

    private boolean hasAllergies;
    private String allergiesDescription;

    private boolean hasMedications;
    private String medicationsDescription;

    private boolean hasSensitivities;
    private String sensitivitiesDescription;

    private String observations;

    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
