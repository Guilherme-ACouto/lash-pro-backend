package com.bravapro.services.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;

public class ServiceAlreadyExistsException extends BusinessException {
    public ServiceAlreadyExistsException(String name) {
        super("Já existe um serviço com o nome: " + name);
    }
}
