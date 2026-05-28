package com.lashmanager.app.domain.exception;

import java.util.UUID;

public class ServiceNotFoundException extends DomainException {
    public ServiceNotFoundException(UUID id) {
        super("Serviço não encontrado: " + id);
    }
}
