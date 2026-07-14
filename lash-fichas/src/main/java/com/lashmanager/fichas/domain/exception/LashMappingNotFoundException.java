package com.lashmanager.fichas.domain.exception;

import com.lashmanager.core.domain.exception.DomainException;

import java.util.UUID;

public class LashMappingNotFoundException extends DomainException {
    public LashMappingNotFoundException(UUID id) {
        super("Mapeamento não encontrado: " + id);
    }
}
