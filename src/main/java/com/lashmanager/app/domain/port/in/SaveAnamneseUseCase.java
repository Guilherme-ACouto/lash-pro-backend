package com.lashmanager.app.domain.port.in;

import java.time.LocalDate;
import java.util.UUID;

public interface SaveAnamneseUseCase {
    record SaveAnamneseCommand(
        UUID clientId,
        String guardianName, String address, String neighborhood,
        String city, String state, LocalDate birthDate,
        String phone, String cpf, String rg,
        boolean hadLashExtensions, boolean wearsMascara,
        boolean hasAllergies, boolean hasThyroidIssues,
        String sleepSide, boolean hadEyeProcedure,
        boolean isPregnantOrNursing, boolean hadOncologicalTreatment,
        boolean hasSkinDisease, boolean hasHealthTreatment,
        boolean usesMedication, boolean termAccepted
    ) {}

    GetOrCreateAnamneseUseCase.AnamneseResult execute(SaveAnamneseCommand command);
}
