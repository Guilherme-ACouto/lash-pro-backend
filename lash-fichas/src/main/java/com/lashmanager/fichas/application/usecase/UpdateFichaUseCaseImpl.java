package com.lashmanager.fichas.application.usecase;

import com.lashmanager.fichas.domain.exception.FichaNotFoundException;
import com.lashmanager.fichas.domain.model.Ficha;
import com.lashmanager.fichas.domain.port.in.CreateFichaUseCase;
import com.lashmanager.fichas.domain.port.in.UpdateFichaUseCase;
import com.lashmanager.fichas.domain.port.out.FichaRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UpdateFichaUseCaseImpl implements UpdateFichaUseCase {

    private final FichaRepository fichaRepository;

    @Override
    public CreateFichaUseCase.FichaResult execute(UpdateFichaCommand command) {
        Ficha existing = fichaRepository.findById(command.id())
                .orElseThrow(() -> new FichaNotFoundException(command.id()));

        Ficha updated = existing.toBuilder()
                .date(command.date())
                .skinType(command.skinType())
                .eyeShape(command.eyeShape())
                .hasAllergies(command.hasAllergies())
                .allergiesDescription(command.allergiesDescription())
                .hasMedications(command.hasMedications())
                .medicationsDescription(command.medicationsDescription())
                .hasSensitivities(command.hasSensitivities())
                .sensitivitiesDescription(command.sensitivitiesDescription())
                .observations(command.observations())
                .updatedAt(LocalDateTime.now())
                .build();

        return FichaUseCaseMapper.toFichaResult(fichaRepository.save(updated));
    }
}
