package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.Anamnese;
import com.lashmanager.app.domain.port.in.GetOrCreateAnamneseUseCase;
import com.lashmanager.app.domain.port.in.SaveAnamneseUseCase;
import com.lashmanager.app.domain.port.out.AnamneseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class SaveAnamneseUseCaseImpl implements SaveAnamneseUseCase {
    private final AnamneseRepository anamneseRepository;
    private final GetOrCreateAnamneseUseCaseImpl getOrCreate;

    @Override public GetOrCreateAnamneseUseCase.AnamneseResult execute(SaveAnamneseCommand command) {
        Anamnese existing = anamneseRepository.findByClientId(command.clientId()).orElse(null);
        UUID existingId = existing != null ? existing.getId() : null;

        LocalDateTime termAcceptedAt = null;
        if (command.termAccepted()) {
            termAcceptedAt = existing != null && existing.isTermAccepted() && existing.getTermAcceptedAt() != null
                ? existing.getTermAcceptedAt() : LocalDateTime.now();
        }

        Anamnese toSave = Anamnese.builder()
            .id(existingId).clientId(command.clientId())
            .guardianName(command.guardianName()).address(command.address())
            .neighborhood(command.neighborhood()).city(command.city())
            .state(command.state()).birthDate(command.birthDate())
            .phone(command.phone()).cpf(command.cpf()).rg(command.rg())
            .hadLashExtensions(command.hadLashExtensions()).wearsMascara(command.wearsMascara())
            .hasAllergies(command.hasAllergies()).hasThyroidIssues(command.hasThyroidIssues())
            .sleepSide(command.sleepSide() != null ? command.sleepSide() : "AMBOS")
            .hadEyeProcedure(command.hadEyeProcedure())
            .isPregnantOrNursing(command.isPregnantOrNursing())
            .hadOncologicalTreatment(command.hadOncologicalTreatment())
            .hasSkinDisease(command.hasSkinDisease()).hasHealthTreatment(command.hasHealthTreatment())
            .usesMedication(command.usesMedication()).termAccepted(command.termAccepted())
            .termAcceptedAt(termAcceptedAt)
            .createdAt(existing != null ? existing.getCreatedAt() : null)
            .build();

        return GetOrCreateAnamneseUseCaseImpl.toResult(anamneseRepository.save(toSave));
    }
}
