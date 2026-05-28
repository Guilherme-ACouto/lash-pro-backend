package com.lashmanager.app.domain.port.in;

import java.util.UUID;

public interface GetOrCreateAnamneseUseCase {
    record AnamneseResult(
        UUID id, UUID clientId, String clientName,
        String guardianName, String address, String neighborhood,
        String city, String state, String birthDate,
        String phone, String cpf, String rg,
        boolean hadLashExtensions, boolean wearsMascara,
        boolean hasAllergies, boolean hasThyroidIssues,
        String sleepSide, boolean hadEyeProcedure,
        boolean isPregnantOrNursing, boolean hadOncologicalTreatment,
        boolean hasSkinDisease, boolean hasHealthTreatment,
        boolean usesMedication, boolean termAccepted,
        String termAcceptedAt, String createdAt, String updatedAt
    ) {}

    AnamneseResult execute(UUID clientId);
}
