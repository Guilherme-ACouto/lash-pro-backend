package com.lashmanager.core.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Ponto de entrada pra respostas HTTP de sucesso no padrão Pontta: corpo = a própria entidade de
 * domínio (sem DTO de resposta), sinalizando o tipo de operação via header {@code X-lash-alert}
 * em vez de um envelope de mensagem no corpo. Erros continuam pelo {@code GlobalExceptionHandler}
 * (ver {@code com.lashmanager.core.adapter.web.dto.Error}) — {@code RestUtils} cobre só sucesso.
 */
public final class RestUtils {

    private RestUtils() {}

    public static ResponseMessageBuilder message() {
        return new ResponseMessageBuilder();
    }

    public static <T> ResponseEntity<T> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
