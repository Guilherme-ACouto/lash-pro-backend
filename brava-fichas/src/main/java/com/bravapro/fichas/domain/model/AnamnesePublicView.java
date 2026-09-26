package com.bravapro.fichas.domain.model;

/** Resposta do endpoint público (`GET /api/public/anamnese/{token}`), sem autenticação. */
public record AnamnesePublicView(String clientName, String clientPhone, Anamnese anamnese) {}
