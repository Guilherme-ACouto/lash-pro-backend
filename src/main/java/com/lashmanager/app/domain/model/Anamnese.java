package com.lashmanager.app.domain.model;

import lombok.*;
import java.time.*;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class Anamnese {
    private UUID id;
    private UUID clientId;
    private String clientName;
    private String guardianName;
    private String address;
    private String neighborhood;
    private String city;
    private String state;
    private LocalDate birthDate;
    private String phone;
    private String cpf;
    private String rg;
    private boolean hadLashExtensions;
    private boolean wearsMascara;
    private boolean hasAllergies;
    private boolean hasThyroidIssues;
    private String sleepSide;
    private boolean hadEyeProcedure;
    private boolean isPregnantOrNursing;
    private boolean hadOncologicalTreatment;
    private boolean hasSkinDisease;
    private boolean hasHealthTreatment;
    private boolean usesMedication;
    private boolean termAccepted;
    private LocalDateTime termAcceptedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
