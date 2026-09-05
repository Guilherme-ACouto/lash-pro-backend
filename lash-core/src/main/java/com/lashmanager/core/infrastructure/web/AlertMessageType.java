package com.lashmanager.core.infrastructure.web;

import org.springframework.http.HttpStatus;

/**
 * Tipo de alerta de sucesso enviado no header {@code X-lash-alert} — chave i18n no formato
 * {@code "%s.created"/"%s.updated"/"%s.deleted"}, igual ao padrão do Pontta.
 *
 * <p>{@code UPDATED} também cobre operações de mudança de estado que não são um "update" de
 * formulário no sentido estrito (ex.: desativar/reativar) — o agregado continua existindo,
 * só muda de estado, o que é semanticamente mais próximo de "updated" que de "created"/"deleted".
 * Essa cobertura é uma extensão do Lash: o Pontta só documenta os 3 casos de CRUD puro.
 */
public enum AlertMessageType {
    CREATED("%s.created", HttpStatus.CREATED),
    UPDATED("%s.updated", HttpStatus.OK),
    DELETED("%s.deleted", HttpStatus.NO_CONTENT);

    private final String messageFormat;
    private final HttpStatus status;

    AlertMessageType(String messageFormat, HttpStatus status) {
        this.messageFormat = messageFormat;
        this.status = status;
    }

    public String format(String entityName) {
        return String.format(messageFormat, entityName);
    }

    public HttpStatus status() {
        return status;
    }
}
