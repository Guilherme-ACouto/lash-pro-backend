package com.lashmanager.fichas.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.fichas.domain.port.in.CreateFichaUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFichaCommand extends AbstractCommand {

    @NotNull
    private UUID clientId;

    @NotNull
    private LocalDate date;

    private String skinType;
    private String eyeShape;
    private boolean hasAllergies;
    private String allergiesDescription;
    private boolean hasMedications;
    private String medicationsDescription;
    private boolean hasSensitivities;
    private String sensitivitiesDescription;
    private String observations;

    public CreateFichaUseCase.CreateFichaCommand toDomainCommand() {
        return new CreateFichaUseCase.CreateFichaCommand(
                clientId, date, skinType, eyeShape,
                hasAllergies, allergiesDescription,
                hasMedications, medicationsDescription,
                hasSensitivities, sensitivitiesDescription,
                observations);
    }
}
