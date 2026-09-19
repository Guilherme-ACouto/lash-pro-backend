package com.lashmanager.fichas.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;

public class AnamneseLinkExpiredException extends BusinessException {
    public AnamneseLinkExpiredException() {
        super("Link de anamnese expirado — peça um novo link.");
    }
}
