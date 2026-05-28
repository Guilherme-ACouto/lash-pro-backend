package com.lashmanager.app.domain.exception;

import java.util.UUID;

public class AnamneseNotFoundException extends DomainException {
    public AnamneseNotFoundException(UUID id) {
        super("Anamnese não encontrada: " + id);
    }
}
