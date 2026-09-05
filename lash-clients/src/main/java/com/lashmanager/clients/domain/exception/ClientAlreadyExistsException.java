package com.lashmanager.clients.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;

public class ClientAlreadyExistsException extends BusinessException {
    public ClientAlreadyExistsException(String phone) {
        super("Já existe um cliente com o telefone: " + phone);
    }
}
