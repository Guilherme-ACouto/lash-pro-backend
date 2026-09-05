package com.lashmanager.fichas.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.fichas.domain.port.in.CreateLashMappingUseCase;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLashMappingCommand extends AbstractCommand {

  @NotNull private UUID fichaId;

  private UUID appointmentId;

  @NotNull private LocalDate date;

  private String technique;
  private String curvature;
  private String thickness;
  private String length;
  private String rightEyeNotes;
  private String leftEyeNotes;
  private String notes;

  public CreateLashMappingUseCase.CreateLashMappingCommand toDomainCommand() {
    return new CreateLashMappingUseCase.CreateLashMappingCommand(
        fichaId,
        appointmentId,
        date,
        technique,
        curvature,
        thickness,
        length,
        rightEyeNotes,
        leftEyeNotes,
        notes);
  }
}
