package com.lashmanager.fichas.adapter.web.dto;

import com.lashmanager.fichas.domain.model.Anamnese;
import com.lashmanager.fichas.domain.model.SleepSide;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AnamneseResponse(
        UUID id,
        UUID clientId,
        String clientName,
        String guardianName,
        String address,
        String neighborhood,
        String city,
        String state,
        LocalDate birthDate,
        String phone,
        String cpf,
        String rg,
        boolean hadLashExtensions,
        boolean wearsMascara,
        boolean hasAllergies,
        boolean hasThyroidIssues,
        SleepSide sleepSide,
        boolean hadEyeProcedure,
        boolean isPregnantOrNursing,
        boolean hadOncologicalTreatment,
        boolean hasSkinDisease,
        boolean hasHealthTreatment,
        boolean usesMedication,
        boolean termAccepted,
        LocalDateTime termAcceptedAt,
        String linkToken,
        LocalDateTime linkTokenExpiry,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public static AnamneseResponse from(Anamnese anamnese) {
        return new AnamneseResponse(
                anamnese.getId(),
                anamnese.getClientId(),
                anamnese.getClientName(),
                anamnese.getGuardianName(),
                anamnese.getAddress(),
                anamnese.getNeighborhood(),
                anamnese.getCity(),
                anamnese.getState(),
                anamnese.getBirthDate(),
                anamnese.getPhone(),
                anamnese.getCpf(),
                anamnese.getRg(),
                anamnese.isHadLashExtensions(),
                anamnese.isWearsMascara(),
                anamnese.isHasAllergies(),
                anamnese.isHasThyroidIssues(),
                anamnese.getSleepSide(),
                anamnese.isHadEyeProcedure(),
                anamnese.isPregnantOrNursing(),
                anamnese.isHadOncologicalTreatment(),
                anamnese.isHasSkinDisease(),
                anamnese.isHasHealthTreatment(),
                anamnese.isUsesMedication(),
                anamnese.isTermAccepted(),
                anamnese.getTermAcceptedAt(),
                anamnese.getLinkToken(),
                anamnese.getLinkTokenExpiry(),
                anamnese.getCreatedAt(),
                anamnese.getUpdatedAt());
    }
}
