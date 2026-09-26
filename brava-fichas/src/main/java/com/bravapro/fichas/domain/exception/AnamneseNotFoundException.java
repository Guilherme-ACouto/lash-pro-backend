package com.bravapro.fichas.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;
import java.util.UUID;

public class AnamneseNotFoundException extends BusinessException {
    public AnamneseNotFoundException(UUID clientId) {
        super("Anamnese não encontrada para o cliente: " + clientId);
    }
}
