package com.bravapro.fichas.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;

public class AnamneseLinkExpiredException extends BusinessException {
    public AnamneseLinkExpiredException() {
        super("Link de anamnese expirado — peça um novo link.");
    }
}
