package com.bravapro.fichas.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;

public class AnamneseLinkInvalidException extends BusinessException {
    public AnamneseLinkInvalidException() {
        super("Link de anamnese inválido.");
    }
}
