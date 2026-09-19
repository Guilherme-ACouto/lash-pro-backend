package com.lashmanager.fichas.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;

public class AnamneseLinkInvalidException extends BusinessException {
    public AnamneseLinkInvalidException() {
        super("Link de anamnese inválido.");
    }
}
