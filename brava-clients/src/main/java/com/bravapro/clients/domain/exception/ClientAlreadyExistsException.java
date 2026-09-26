package com.bravapro.clients.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;

public class ClientAlreadyExistsException extends BusinessException {
    public ClientAlreadyExistsException(String phone) {
        super("Já existe um cliente com o telefone: " + phone);
    }
}
