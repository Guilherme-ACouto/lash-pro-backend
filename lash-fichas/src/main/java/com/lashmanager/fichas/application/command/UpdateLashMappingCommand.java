package com.lashmanager.fichas.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.fichas.domain.port.in.UpdateLashMappingUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLashMappingCommand extends AbstractCommand {

    @NotNull
    private UUID id;

    @NotNull
    private LocalDate date;

    private String technique;
    private String curvature;
    private String thickness;
    private String length;
    private String rightEyeNotes;
    private String leftEyeNotes;
    private String notes;

    public UpdateLashMappingUseCase.UpdateLashMappingCommand toDomainCommand() {
        return new UpdateLashMappingUseCase.UpdateLashMappingCommand(
                id, date, technique, curvature, thickness, length, rightEyeNotes, leftEyeNotes, notes);
    }
}
