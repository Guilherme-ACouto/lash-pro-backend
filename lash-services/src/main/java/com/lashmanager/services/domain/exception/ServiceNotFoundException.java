package com.lashmanager.services.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;

import java.util.UUID;

public class ServiceNotFoundException extends BusinessException {
    public ServiceNotFoundException(UUID id) {
        super("Serviço não encontrado: " + id);
    }
}
