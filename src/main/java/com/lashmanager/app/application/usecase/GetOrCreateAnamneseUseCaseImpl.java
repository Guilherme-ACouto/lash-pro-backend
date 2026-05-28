package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.Anamnese;
import com.lashmanager.app.domain.port.in.GetOrCreateAnamneseUseCase;
import com.lashmanager.app.domain.port.out.AnamneseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class GetOrCreateAnamneseUseCaseImpl implements GetOrCreateAnamneseUseCase {
    private final AnamneseRepository anamneseRepository;

    @Override public AnamneseResult execute(UUID clientId) {
        Anamnese anamnese = anamneseRepository.findByClientId(clientId)
            .orElseGet(() -> {
                Anamnese novo = Anamnese.builder()
                    .clientId(clientId).sleepSide("AMBOS")
                    .termAccepted(false).build();
                return anamneseRepository.save(novo);
            });
        return toResult(anamnese);
    }

    public static AnamneseResult toResult(Anamnese a) {
        return new AnamneseResult(
            a.getId(), a.getClientId(), a.getClientName(),
            a.getGuardianName(), a.getAddress(), a.getNeighborhood(),
            a.getCity(), a.getState(),
            a.getBirthDate() != null ? a.getBirthDate().toString() : null,
            a.getPhone(), a.getCpf(), a.getRg(),
            a.isHadLashExtensions(), a.isWearsMascara(),
            a.isHasAllergies(), a.isHasThyroidIssues(),
            a.getSleepSide(), a.isHadEyeProcedure(),
            a.isPregnantOrNursing(), a.isHadOncologicalTreatment(),
            a.isHasSkinDisease(), a.isHasHealthTreatment(),
            a.isUsesMedication(), a.isTermAccepted(),
            a.getTermAcceptedAt() != null ? a.getTermAcceptedAt().toString() : null,
            a.getCreatedAt() != null ? a.getCreatedAt().toString() : null,
            a.getUpdatedAt() != null ? a.getUpdatedAt().toString() : null
        );
    }
}
