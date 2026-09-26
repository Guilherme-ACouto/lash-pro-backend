package com.bravapro.fichas.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Projeção de leitura pra listagem — junta todos os clientes (mesmo os que ainda não preencheram
 * anamnese) com o status da anamnese de cada um. Não é o agregado {@code Anamnese} (que só existe
 * pra quem já tem uma linha criada) — mora do lado de {@code AnamneseQueryService}.
 */
public record AnamneseSummary(UUID clientId, String clientName, boolean hasAnamnese, LocalDateTime updatedAt) {}
