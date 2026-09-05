package com.lashmanager.core.infrastructure.web;

import com.lashmanager.core.domain.model.DomainEntity;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

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
        HttpHeaders headers = HEADER_BUILDER.createAlert(AlertMessageType.UPDATED.format(entityName), id.toString());
        return ResponseEntity.status(AlertMessageType.UPDATED.status())
                .headers(headers)
                .build();
    }

    public ResponseEntity<Void> deleted(String entityName, UUID id) {
        HttpHeaders headers = HEADER_BUILDER.createAlert(AlertMessageType.DELETED.format(entityName), id.toString());
        return ResponseEntity.status(AlertMessageType.DELETED.status())
                .headers(headers)
                .build();
    }

    private ResponseEntity<Object> response(AlertMessageType type, String entityName, DomainEntity body) {
        HttpHeaders headers =
                HEADER_BUILDER.createAlert(type.format(entityName), body.getId().toString());
        return ResponseEntity.status(type.status()).headers(headers).body(body);
    }
}
