package com.lashmanager.app.domain.exception;

import java.util.UUID;

public class ClientNotFoundException extends DomainException {
    public ClientNotFoundException(UUID id) {
        super("Cliente não encontrado: " + id);
    }
}
