package com.lashmanager.fichas.application.service;

import com.lashmanager.fichas.application.command.GenerateAnamneseLinkCommand;
import com.lashmanager.fichas.application.command.SaveAnamneseCommand;
import com.lashmanager.fichas.domain.model.Anamnese;
import com.lashmanager.fichas.domain.model.GeneratedAnamneseLink;
import com.lashmanager.fichas.domain.port.in.AnamneseUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Autenticada — o fluxo público (token, sem login) fica em {@link PublicAnamneseApplicationService}.
 * `save`/`generateLink` são upsert, então o UseCase resolve busca-ou-cria internamente (não tem
 * uma entidade prévia garantida pra ApplicationService buscar, diferente de update/delete comuns).
 */
@Service
@RequiredArgsConstructor
public class AnamneseApplicationService {

    private final AnamneseUseCase anamneseUseCase;

    public Anamnese when(SaveAnamneseCommand command) {
        return anamneseUseCase.save(command);
    }

    public GeneratedAnamneseLink when(GenerateAnamneseLinkCommand command) {
        return anamneseUseCase.generateLink(command.getClientId());
    }
}
