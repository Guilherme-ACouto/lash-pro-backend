package com.lashmanager.app.domain.port.in;

import java.time.LocalDate;
import java.util.UUID;

public interface UpdateLashMappingUseCase {
    record UpdateMappingCommand(
        UUID id, LocalDate mappingDate,
        String mappingType, String curvature,
        String humidity, String temperature, String thickness,
        String threadBrand, String threadFormat, String adhesive,
        String lengthsUsed, String observations,
        String canvasData, String photoBefore, String photoAfter
    ) {}

    CreateLashMappingUseCase.MappingResult execute(UpdateMappingCommand command);
}
