package com.lashmanager.fichas.infrastructure.persistence.mapper;

import com.lashmanager.fichas.domain.model.Anamnese;
import com.lashmanager.fichas.infrastructure.persistence.entity.AnamneseEntity;
import org.springframework.stereotype.Component;

@Component
public class AnamneseMapper {

    public Anamnese toDomain(AnamneseEntity entity) {
        return Anamnese.builder()
                .id(entity.getId())
                .clientId(entity.getClientId())
                .clientName(entity.getClientName())
                .guardianName(entity.getGuardianName())
                .address(entity.getAddress())
                .neighborhood(entity.getNeighborhood())
                .city(entity.getCity())
                .state(entity.getState())
                .birthDate(entity.getBirthDate())
                .phone(entity.getPhone())
                .cpf(entity.getCpf())
                .rg(entity.getRg())
                .hadLashExtensions(entity.isHadLashExtensions())
                .wearsMascara(entity.isWearsMascara())
                .hasAllergies(entity.isHasAllergies())
                .hasThyroidIssues(entity.isHasThyroidIssues())
                .sleepSide(entity.getSleepSide())
                .hadEyeProcedure(entity.isHadEyeProcedure())
                .isPregnantOrNursing(entity.isPregnantOrNursing())
                .hadOncologicalTreatment(entity.isHadOncologicalTreatment())
                .hasSkinDisease(entity.isHasSkinDisease())
                .hasHealthTreatment(entity.isHasHealthTreatment())
                .usesMedication(entity.isUsesMedication())
                .termAccepted(entity.isTermAccepted())
                .termAcceptedAt(entity.getTermAcceptedAt())
                .linkToken(entity.getLinkToken())
                .linkTokenExpiry(entity.getLinkTokenExpiry())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public AnamneseEntity toEntity(Anamnese domain) {
        return AnamneseEntity.builder()
                .id(domain.getId())
                .clientId(domain.getClientId())
                .clientName(domain.getClientName())
                .guardianName(domain.getGuardianName())
                .address(domain.getAddress())
                .neighborhood(domain.getNeighborhood())
                .city(domain.getCity())
                .state(domain.getState())
                .birthDate(domain.getBirthDate())
                .phone(domain.getPhone())
                .cpf(domain.getCpf())
                .rg(domain.getRg())
                .hadLashExtensions(domain.isHadLashExtensions())
                .wearsMascara(domain.isWearsMascara())
                .hasAllergies(domain.isHasAllergies())
                .hasThyroidIssues(domain.isHasThyroidIssues())
                .sleepSide(domain.getSleepSide())
                .hadEyeProcedure(domain.isHadEyeProcedure())
                .isPregnantOrNursing(domain.isPregnantOrNursing())
                .hadOncologicalTreatment(domain.isHadOncologicalTreatment())
                .hasSkinDisease(domain.isHasSkinDisease())
                .hasHealthTreatment(domain.isHasHealthTreatment())
                .usesMedication(domain.isUsesMedication())
                .termAccepted(domain.isTermAccepted())
                .termAcceptedAt(domain.getTermAcceptedAt())
                .linkToken(domain.getLinkToken())
                .linkTokenExpiry(domain.getLinkTokenExpiry())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
