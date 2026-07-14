package com.lashmanager.fichas.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;

import java.util.UUID;

public class ClientAlreadyHasFichaException extends BusinessException {
    public ClientAlreadyHasFichaException(UUID clientId) {
        super("Cliente já possui ficha de anamnese: " + clientId);
    }
}
