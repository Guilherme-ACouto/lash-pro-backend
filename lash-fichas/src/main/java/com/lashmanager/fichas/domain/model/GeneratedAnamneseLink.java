package com.lashmanager.fichas.domain.model;

/** Resposta de {@code POST /api/anamnese/{clientId}/link} — não é CRUD de agregado, é o
 * resultado de uma ação específica (ver CLAUDE.md, exceção STR-02 do spec de anamnese). */
public record GeneratedAnamneseLink(String url) {}
