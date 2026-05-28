package com.lashmanager.app.domain.exception;

import java.util.UUID;

public class LashMappingNotFoundException extends DomainException {
    public LashMappingNotFoundException(UUID id) {
        super("Ficha de mapping não encontrada: " + id);
    }
}
