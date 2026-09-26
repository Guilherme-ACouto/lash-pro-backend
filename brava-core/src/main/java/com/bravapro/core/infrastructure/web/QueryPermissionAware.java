package com.bravapro.core.infrastructure.web;

/**
 * Implementada por toda QueryResource autenticada: declara a permissão de leitura do recurso
 * (padrão Pontta — a QueryResource responde {@code queryPermission()}). QueryResource pública
 * (ex.: link de anamnese por token) simplesmente não implementa.
 */
public interface QueryPermissionAware {

    QueryPermission queryPermission();
}
