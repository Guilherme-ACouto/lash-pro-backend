package com.lashmanager.fichas.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.fichas.domain.port.in.UpdateFichaUseCase;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFichaCommand extends AbstractCommand {

  @NotNull private UUID id;

  @NotNull private LocalDate date;

  private String skinType;
  private String eyeShape;
  private boolean hasAllergies;
  private String allergiesDescription;
  private boolean hasMedications;
  private String medicationsDescription;
  private boolean hasSensitivities;
  private String sensitivitiesDescription;
  private String observations;

  public UpdateFichaUseCase.UpdateFichaCommand toDomainCommand() {
    return new UpdateFichaUseCase.UpdateFichaCommand(
        id,
        date,
        skinType,
        eyeShape,
        hasAllergies,
        allergiesDescription,
        hasMedications,
        medicationsDescription,
        hasSensitivities,
        sensitivitiesDescription,
        observations);
  }
}
