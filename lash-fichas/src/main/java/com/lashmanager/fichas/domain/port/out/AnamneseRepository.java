package com.lashmanager.fichas.domain.port.out;

import com.lashmanager.fichas.domain.model.Anamnese;

import java.util.Optional;
import java.util.UUID;

/**
 * Porta de escrita. Leitura (listagem, resumo por cliente) foi separada para
 * AnamneseQueryRepository — findByClientId/findByLinkToken continuam aqui porque os use cases de
 * escrita (save/generateLink upsert, submitByToken) precisam do agregado completo antes de mutar.
 */
public interface AnamneseRepository {
    Anamnese save(Anamnese anamnese);

    Optional<Anamnese> findByClientId(UUID clientId);

    Optional<Anamnese> findByLinkToken(String token);
}
