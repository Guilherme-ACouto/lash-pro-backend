package com.bravapro.fichas.infrastructure.persistence.mapper;

import com.bravapro.fichas.domain.model.Mapping;
import com.bravapro.fichas.infrastructure.persistence.entity.MappingEntity;
import org.springframework.stereotype.Component;

@Component
public class MappingMapper {

    public Mapping toDomain(MappingEntity entity) {
        return Mapping.builder()
                .id(entity.getId())
                .clientId(entity.getClientId())
                .clientName(entity.getClientName())
                .mappingDate(entity.getMappingDate())
                .mappingType(entity.getMappingType())
                .curvature(entity.getCurvature())
                .humidity(entity.getHumidity())
                .temperature(entity.getTemperature())
                .thickness(entity.getThickness())
                .threadBrand(entity.getThreadBrand())
                .threadFormat(entity.getThreadFormat())
                .adhesive(entity.getAdhesive())
                .lengthsUsed(entity.getLengthsUsed())
                .observations(entity.getObservations())
                .canvasData(entity.getCanvasData())
                .photoBefore(entity.getPhotoBefore())
                .photoAfter(entity.getPhotoAfter())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public MappingEntity toEntity(Mapping domain) {
        return MappingEntity.builder()
                .id(domain.getId())
                .clientId(domain.getClientId())
                .clientName(domain.getClientName())
                .mappingDate(domain.getMappingDate())
                .mappingType(domain.getMappingType())
                .curvature(domain.getCurvature())
                .humidity(domain.getHumidity())
                .temperature(domain.getTemperature())
                .thickness(domain.getThickness())
                .threadBrand(domain.getThreadBrand())
                .threadFormat(domain.getThreadFormat())
                .adhesive(domain.getAdhesive())
                .lengthsUsed(domain.getLengthsUsed())
                .observations(domain.getObservations())
                .canvasData(domain.getCanvasData())
                .photoBefore(domain.getPhotoBefore())
                .photoAfter(domain.getPhotoAfter())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
