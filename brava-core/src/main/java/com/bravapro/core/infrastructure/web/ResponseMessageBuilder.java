package com.bravapro.core.infrastructure.web;

import com.bravapro.core.domain.model.DomainEntity;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ResponseMessageBuilder {

    private static final HeaderMessageBuilder HEADER_BUILDER = new HeaderMessageBuilder();

    public ResponseEntity<Object> created(String entityName, DomainEntity body) {
        return response(AlertMessageType.CREATED, entityName, body);
    }

    public ResponseEntity<Object> updated(String entityName, DomainEntity body) {
        return response(AlertMessageType.UPDATED, entityName, body);
    }

    /**
     * Overload sem corpo — usada quando o Resource já tem o id da URL e não precisa recarregar
     * a entidade só pra devolver no response (padrão real do Pontta pra update/deactivate/etc.).
     */
    public ResponseEntity<Void> updated(String entityName, UUID id) {
        return response(AlertMessageType.UPDATED, entityName, id);
    }

    public ResponseEntity<Void> deleted(String entityName, UUID id) {
        return response(AlertMessageType.DELETED, entityName, id);
    }

    public ResponseEntity<Void> deactivated(String entityName, UUID id) {
        return response(AlertMessageType.DEACTIVATED, entityName, id);
    }

    public ResponseEntity<Void> reactivated(String entityName, UUID id) {
        return response(AlertMessageType.REACTIVATED, entityName, id);
    }

    private ResponseEntity<Void> response(AlertMessageType type, String entityName, UUID id) {
        MultiValueMap<String, String> headers = HEADER_BUILDER.createAlert(type.format(entityName), id.toString());
        return ResponseEntity.status(type.status()).headers(new HttpHeaders(headers)).build();
    }

    private ResponseEntity<Object> response(AlertMessageType type, String entityName, DomainEntity body) {
        MultiValueMap<String, String> headers =
                HEADER_BUILDER.createAlert(type.format(entityName), body.getId().toString());
        return ResponseEntity.status(type.status()).headers(new HttpHeaders(headers)).body(body);
    }
}
