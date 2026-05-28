package com.lashmanager.app.infrastructure.persistence.mapper;

import com.lashmanager.app.domain.model.LashMapping;
import com.lashmanager.app.infrastructure.persistence.entity.LashMappingEntity;
import org.springframework.stereotype.Component;

@Component
public class LashMappingMapper {
    public LashMapping toDomain(LashMappingEntity entity) {
        if (entity == null) return null;
        return LashMapping.builder()
            .id(entity.getId()).clientId(entity.getClientId())
            .clientName(entity.getClient() != null ? entity.getClient().getName() : null)
            .mappingDate(entity.getMappingDate()).mappingType(entity.getMappingType())
            .curvature(entity.getCurvature()).humidity(entity.getHumidity())
            .temperature(entity.getTemperature()).thickness(entity.getThickness())
            .threadBrand(entity.getThreadBrand()).threadFormat(entity.getThreadFormat())
            .adhesive(entity.getAdhesive()).lengthsUsed(entity.getLengthsUsed())
            .observations(entity.getObservations()).canvasData(entity.getCanvasData())
            .photoBefore(entity.getPhotoBefore()).photoAfter(entity.getPhotoAfter())
            .createdAt(entity.getCreatedAt()).updatedAt(entity.getUpdatedAt()).build();
    }

    public LashMappingEntity toEntity(LashMapping domain) {
        if (domain == null) return null;
        return LashMappingEntity.builder()
            .id(domain.getId()).clientId(domain.getClientId())
            .mappingDate(domain.getMappingDate()).mappingType(domain.getMappingType())
            .curvature(domain.getCurvature()).humidity(domain.getHumidity())
            .temperature(domain.getTemperature()).thickness(domain.getThickness())
            .threadBrand(domain.getThreadBrand()).threadFormat(domain.getThreadFormat())
            .adhesive(domain.getAdhesive()).lengthsUsed(domain.getLengthsUsed())
            .observations(domain.getObservations()).canvasData(domain.getCanvasData())
            .photoBefore(domain.getPhotoBefore()).photoAfter(domain.getPhotoAfter())
            .createdAt(domain.getCreatedAt()).updatedAt(domain.getUpdatedAt()).build();
    }
}
