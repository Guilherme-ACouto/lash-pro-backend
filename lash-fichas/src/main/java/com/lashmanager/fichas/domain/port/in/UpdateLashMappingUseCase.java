package com.lashmanager.fichas.domain.port.in;

import java.time.LocalDate;
import java.util.UUID;

public interface UpdateLashMappingUseCase {

    record UpdateLashMappingCommand(
            UUID id,
            LocalDate date,
            String technique,
            String curvature,
            String thickness,
            String length,
            String rightEyeNotes,
            String leftEyeNotes,
            String notes
    ) {}

    CreateLashMappingUseCase.LashMappingResult execute(UpdateLashMappingCommand command);
}
