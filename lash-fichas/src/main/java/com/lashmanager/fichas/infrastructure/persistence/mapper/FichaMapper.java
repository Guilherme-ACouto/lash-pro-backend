package com.lashmanager.fichas.infrastructure.persistence.mapper;

import com.lashmanager.fichas.domain.model.Ficha;
import com.lashmanager.fichas.infrastructure.persistence.entity.FichaEntity;
import org.springframework.stereotype.Component;

@Component
public class FichaMapper {

    public Ficha toDomain(FichaEntity e) {
        return Ficha.builder()
                .id(e.getId())
                .clientId(e.getClientId())
                .clientName(e.getClientName())
                .date(e.getDate())
                .skinType(e.getSkinType())
                .eyeShape(e.getEyeShape())
                .hasAllergies(e.isHasAllergies())
                .allergiesDescription(e.getAllergiesDescription())
                .hasMedications(e.isHasMedications())
                .medicationsDescription(e.getMedicationsDescription())
                .hasSensitivities(e.isHasSensitivities())
                .sensitivitiesDescription(e.getSensitivitiesDescription())
                .observations(e.getObservations())
                .active(e.isActive())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public FichaEntity toEntity(Ficha f) {
        return FichaEntity.builder()
                .id(f.getId())
                .clientId(f.getClientId())
                .clientName(f.getClientName())
                .date(f.getDate())
                .skinType(f.getSkinType())
                .eyeShape(f.getEyeShape())
                .hasAllergies(f.isHasAllergies())
                .allergiesDescription(f.getAllergiesDescription())
                .hasMedications(f.isHasMedications())
                .medicationsDescription(f.getMedicationsDescription())
                .hasSensitivities(f.isHasSensitivities())
                .sensitivitiesDescription(f.getSensitivitiesDescription())
                .observations(f.getObservations())
                .active(f.isActive())
                .createdAt(f.getCreatedAt())
                .updatedAt(f.getUpdatedAt())
                .build();
    }
}
