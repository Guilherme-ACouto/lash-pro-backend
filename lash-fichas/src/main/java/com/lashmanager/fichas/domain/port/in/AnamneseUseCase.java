package com.lashmanager.fichas.domain.port.in;

import com.lashmanager.fichas.application.command.SaveAnamneseCommand;
import com.lashmanager.fichas.domain.model.Anamnese;
import com.lashmanager.fichas.domain.model.GeneratedAnamneseLink;

import java.util.UUID;

public interface AnamneseUseCase {

    /** Upsert — busca-ou-cria por {@code command.getClientId()} internamente. */
    Anamnese save(SaveAnamneseCommand command);

    /** Recebe a entidade já carregada (usado pelo fluxo público, onde quem busca é a
     * ApplicationService a partir do token). */
    Anamnese update(Anamnese anamnese, SaveAnamneseCommand command);

    /** Também upsert — o link pode ser gerado antes do cliente preencher qualquer campo. */
    GeneratedAnamneseLink generateLink(UUID clientId);
}
