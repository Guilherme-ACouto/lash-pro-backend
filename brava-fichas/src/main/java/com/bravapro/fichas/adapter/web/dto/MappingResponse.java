package com.bravapro.fichas.adapter.web.dto;

import com.bravapro.fichas.domain.model.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record MappingResponse(
        UUID id,
        UUID clientId,
        String clientName,
        LocalDate mappingDate,
        String mappingType,
        String curvature,
        String humidity,
        String temperature,
        String thickness,
        String threadBrand,
        String threadFormat,
        String adhesive,
        String lengthsUsed,
        String observations,
        String canvasData,
        String photoBefore,
        String photoAfter,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public static MappingResponse from(Mapping mapping) {
        return new MappingResponse(
                mapping.getId(),
                mapping.getClientId(),
                mapping.getClientName(),
                mapping.getMappingDate(),
                mapping.getMappingType(),
                mapping.getCurvature(),
                mapping.getHumidity(),
                mapping.getTemperature(),
                mapping.getThickness(),
                mapping.getThreadBrand(),
                mapping.getThreadFormat(),
                mapping.getAdhesive(),
                mapping.getLengthsUsed(),
                mapping.getObservations(),
                mapping.getCanvasData(),
                mapping.getPhotoBefore(),
                mapping.getPhotoAfter(),
                mapping.getCreatedAt(),
                mapping.getUpdatedAt());
    }
}
