package com.bravapro.core.infrastructure.web;

import org.springframework.http.HttpStatus;

/**
 * Tipo de alerta de sucesso enviado no header {@code X-bravapro-alert} — chave i18n no formato
 * {@code "%s.created"/"%s.updated"/"%s.deleted"}, igual ao padrão do Pontta.
 *
 * <p>{@code DEACTIVATED}/{@code REACTIVATED} são extensão do Brava Pro sobre os 3 casos de CRUD do
 * Pontta: sem elas, desativar/reativar caíam em {@code UPDATED} e o front não conseguia mostrar
 * uma mensagem específica pra cada ação.
 */
public enum AlertMessageType {
    CREATED("%s.created", HttpStatus.CREATED),
    UPDATED("%s.updated", HttpStatus.OK),
    DELETED("%s.deleted", HttpStatus.NO_CONTENT),
    DEACTIVATED("%s.deactivated", HttpStatus.OK),
    REACTIVATED("%s.reactivated", HttpStatus.OK);

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
