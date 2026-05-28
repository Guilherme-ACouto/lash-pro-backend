package com.lashmanager.app.infrastructure.persistence.mapper;

import com.lashmanager.app.domain.model.Anamnese;
import com.lashmanager.app.infrastructure.persistence.entity.AnamneseEntity;
import org.springframework.stereotype.Component;

@Component
public class AnamneseMapper {
    public Anamnese toDomain(AnamneseEntity entity) {
        if (entity == null) return null;
        return Anamnese.builder()
            .id(entity.getId()).clientId(entity.getClientId())
            .clientName(entity.getClient() != null ? entity.getClient().getName() : null)
            .guardianName(entity.getGuardianName()).address(entity.getAddress())
            .neighborhood(entity.getNeighborhood()).city(entity.getCity())
            .state(entity.getState()).birthDate(entity.getBirthDate())
            .phone(entity.getPhone()).cpf(entity.getCpf()).rg(entity.getRg())
            .hadLashExtensions(entity.isHadLashExtensions()).wearsMascara(entity.isWearsMascara())
            .hasAllergies(entity.isHasAllergies()).hasThyroidIssues(entity.isHasThyroidIssues())
            .sleepSide(entity.getSleepSide()).hadEyeProcedure(entity.isHadEyeProcedure())
            .isPregnantOrNursing(entity.isPregnantOrNursing())
            .hadOncologicalTreatment(entity.isHadOncologicalTreatment())
            .hasSkinDisease(entity.isHasSkinDisease()).hasHealthTreatment(entity.isHasHealthTreatment())
            .usesMedication(entity.isUsesMedication()).termAccepted(entity.isTermAccepted())
            .termAcceptedAt(entity.getTermAcceptedAt()).createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt()).build();
    }

    public AnamneseEntity toEntity(Anamnese domain) {
        if (domain == null) return null;
        return AnamneseEntity.builder()
            .id(domain.getId()).clientId(domain.getClientId())
            .guardianName(domain.getGuardianName()).address(domain.getAddress())
            .neighborhood(domain.getNeighborhood()).city(domain.getCity())
            .state(domain.getState()).birthDate(domain.getBirthDate())
            .phone(domain.getPhone()).cpf(domain.getCpf()).rg(domain.getRg())
            .hadLashExtensions(domain.isHadLashExtensions()).wearsMascara(domain.isWearsMascara())
            .hasAllergies(domain.isHasAllergies()).hasThyroidIssues(domain.isHasThyroidIssues())
            .sleepSide(domain.getSleepSide() != null ? domain.getSleepSide() : "AMBOS")
            .hadEyeProcedure(domain.isHadEyeProcedure())
            .isPregnantOrNursing(domain.isPregnantOrNursing())
            .hadOncologicalTreatment(domain.isHadOncologicalTreatment())
            .hasSkinDisease(domain.isHasSkinDisease()).hasHealthTreatment(domain.isHasHealthTreatment())
            .usesMedication(domain.isUsesMedication()).termAccepted(domain.isTermAccepted())
            .termAcceptedAt(domain.getTermAcceptedAt()).createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt()).build();
    }
}
