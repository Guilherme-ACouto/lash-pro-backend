package com.bravapro.services.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;
import java.util.UUID;

public class ServiceNotFoundException extends BusinessException {
    public ServiceNotFoundException(UUID id) {
        super("Serviço não encontrado: " + id);
    }
}
