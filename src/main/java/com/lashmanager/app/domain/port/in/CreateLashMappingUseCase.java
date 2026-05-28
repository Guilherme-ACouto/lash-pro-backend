package com.lashmanager.app.domain.port.in;

import java.time.LocalDate;
import java.util.UUID;

public interface CreateLashMappingUseCase {
    record CreateMappingCommand(
        UUID clientId, LocalDate mappingDate,
        String mappingType, String curvature,
        String humidity, String temperature, String thickness,
        String threadBrand, String threadFormat, String adhesive,
        String lengthsUsed, String observations,
        String canvasData, String photoBefore, String photoAfter
    ) {}

    MappingResult execute(CreateMappingCommand command);

    record MappingResult(
        String id, String clientId, String clientName,
        String mappingDate, String mappingType, String curvature,
        String humidity, String temperature, String thickness,
        String threadBrand, String threadFormat, String adhesive,
        String lengthsUsed, String observations,
        String canvasData, String photoBefore, String photoAfter,
        String createdAt, String updatedAt
    ) {}
}
