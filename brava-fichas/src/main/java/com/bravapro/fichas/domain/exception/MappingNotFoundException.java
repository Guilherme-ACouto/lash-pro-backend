package com.bravapro.fichas.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;
import java.util.UUID;

public class MappingNotFoundException extends BusinessException {
    public MappingNotFoundException(UUID id) {
        super("Mapeamento não encontrado: " + id);
    }
}
