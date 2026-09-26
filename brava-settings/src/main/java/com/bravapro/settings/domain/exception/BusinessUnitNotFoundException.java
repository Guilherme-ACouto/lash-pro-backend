package com.bravapro.settings.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;

public class BusinessUnitNotFoundException extends BusinessException {
    public BusinessUnitNotFoundException() {
        super("Unidade de negócio não encontrada");
    }
}
