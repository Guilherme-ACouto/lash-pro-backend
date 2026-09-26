package com.bravapro.core.domain.model;

import java.util.UUID;

/** Tokens emitidos pra equipe da plataforma operar dentro de uma assinatura. */
public record SupportSession(String accessToken, String refreshToken, UUID tenantId, String tenantName) {}
