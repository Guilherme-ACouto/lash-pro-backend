package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.AnamneseTokenNotFoundException;
import com.lashmanager.app.domain.model.AnamneseToken;
import com.lashmanager.app.domain.port.in.GetAnamneseByTokenUseCase;
import com.lashmanager.app.domain.port.in.GetOrCreateAnamneseUseCase;
import com.lashmanager.app.domain.port.out.AnamneseRepository;
import com.lashmanager.app.domain.port.out.AnamneseTokenRepository;
import com.lashmanager.app.infrastructure.persistence.repository.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class GetAnamneseByTokenUseCaseImpl implements GetAnamneseByTokenUseCase {
    private final AnamneseTokenRepository tokenRepository;
    private final AnamneseRepository anamneseRepository;
    private final ClientJpaRepository clientJpaRepository;

    @Override public AnamneseTokenResult execute(String token) {
        AnamneseToken tokenEntity = tokenRepository.findByToken(token)
            .orElseThrow(AnamneseTokenNotFoundException::new);

        var client = clientJpaRepository.findById(tokenEntity.getClientId())
            .orElseThrow(AnamneseTokenNotFoundException::new);

        var anamnese = anamneseRepository.findByClientId(tokenEntity.getClientId())
            .map(GetOrCreateAnamneseUseCaseImpl::toResult)
            .orElse(null);

        return new AnamneseTokenResult(client.getName(), client.getPhone(), anamnese);
    }
}
