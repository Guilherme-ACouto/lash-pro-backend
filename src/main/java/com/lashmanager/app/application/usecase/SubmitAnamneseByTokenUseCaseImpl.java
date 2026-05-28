package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.AnamneseTokenNotFoundException;
import com.lashmanager.app.domain.model.AnamneseToken;
import com.lashmanager.app.domain.port.in.SaveAnamneseUseCase;
import com.lashmanager.app.domain.port.in.SubmitAnamneseByTokenUseCase;
import com.lashmanager.app.domain.port.out.AnamneseTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class SubmitAnamneseByTokenUseCaseImpl implements SubmitAnamneseByTokenUseCase {
    private final AnamneseTokenRepository tokenRepository;
    private final SaveAnamneseUseCaseImpl saveAnamnese;

    @Override @Transactional
    public void execute(SubmitAnamneseCommand command) {
        AnamneseToken tokenEntity = tokenRepository.findByToken(command.token())
            .orElseThrow(AnamneseTokenNotFoundException::new);

        saveAnamnese.execute(new SaveAnamneseUseCase.SaveAnamneseCommand(
            tokenEntity.getClientId(),
            command.guardianName(), command.address(), command.neighborhood(),
            command.city(), command.state(), command.birthDate(),
            command.phone(), command.cpf(), command.rg(),
            command.hadLashExtensions(), command.wearsMascara(),
            command.hasAllergies(), command.hasThyroidIssues(),
            command.sleepSide(), command.hadEyeProcedure(),
            command.isPregnantOrNursing(), command.hadOncologicalTreatment(),
            command.hasSkinDisease(), command.hasHealthTreatment(),
            command.usesMedication(), command.termAccepted()
        ));

        AnamneseToken used = AnamneseToken.builder()
            .id(tokenEntity.getId()).clientId(tokenEntity.getClientId())
            .token(tokenEntity.getToken()).expiresAt(tokenEntity.getExpiresAt())
            .used(true).createdAt(tokenEntity.getCreatedAt()).build();
        tokenRepository.save(used);
    }
}
