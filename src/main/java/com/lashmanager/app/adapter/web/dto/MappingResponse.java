package com.lashmanager.app.adapter.web.dto;

public record MappingResponse(
    String id,
    String clientId,
    String clientName,
    String mappingDate,
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
    String createdAt,
    String updatedAt
) {}
