package com.lashmanager.fichas.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;
import java.util.UUID;

public class AnamneseNotFoundException extends BusinessException {
    public AnamneseNotFoundException(UUID clientId) {
        super("Anamnese não encontrada para o cliente: " + clientId);
    }
}
