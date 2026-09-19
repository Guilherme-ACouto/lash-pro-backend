package com.lashmanager.fichas.application.service;

import com.lashmanager.fichas.application.command.SubmitAnamneseByTokenCommand;
import com.lashmanager.fichas.domain.exception.AnamneseLinkExpiredException;
import com.lashmanager.fichas.domain.exception.AnamneseLinkInvalidException;
import com.lashmanager.fichas.domain.model.Anamnese;
import com.lashmanager.fichas.domain.port.in.AnamneseUseCase;
import com.lashmanager.fichas.domain.port.out.AnamneseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Sem autenticação ({@code /api/public/anamnese/**}, já liberado no {@code SecurityConfig}) —
 * quem identifica o cliente é o token, não um id de rota autenticada. Aqui a busca acontece na
 * ApplicationService (igual ao padrão de update autenticado) porque, diferente de
 * {@link AnamneseApplicationService#when(com.lashmanager.fichas.application.command.SaveAnamneseCommand)},
 * o token só existe se a anamnese já foi criada (via generateLink) — não há caso de "criar do
 * zero" aqui.
 */
@Service
@RequiredArgsConstructor
public class PublicAnamneseApplicationService {

    private final AnamneseUseCase anamneseUseCase;
    private final AnamneseRepository anamneseRepository;

    public void when(SubmitAnamneseByTokenCommand command) {
        Anamnese anamnese =
                anamneseRepository.findByLinkToken(command.getToken()).orElseThrow(AnamneseLinkInvalidException::new);
        if (anamnese.isLinkExpired()) {
            throw new AnamneseLinkExpiredException();
        }
        anamneseUseCase.update(anamnese, command.toSaveCommand(anamnese.getClientId()));
    }
}
