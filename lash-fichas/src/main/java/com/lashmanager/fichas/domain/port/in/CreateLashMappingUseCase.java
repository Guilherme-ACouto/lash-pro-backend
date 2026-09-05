package com.lashmanager.fichas.domain.port.in;

import java.time.LocalDate;
import java.util.UUID;

public interface CreateLashMappingUseCase {

    record CreateLashMappingCommand(
            UUID fichaId,
            UUID appointmentId,
            LocalDate date,
            String technique,
            String curvature,
            String thickness,
            String length,
            String rightEyeNotes,
            String leftEyeNotes,
            String notes) {}

    record LashMappingResult(
            UUID id,
            UUID fichaId,
            UUID appointmentId,
            String date,
            String technique,
            String curvature,
            String thickness,
            String length,
            String rightEyeNotes,
            String leftEyeNotes,
            String notes,
            String createdAt) {}

    LashMappingResult execute(CreateLashMappingCommand command);
}
