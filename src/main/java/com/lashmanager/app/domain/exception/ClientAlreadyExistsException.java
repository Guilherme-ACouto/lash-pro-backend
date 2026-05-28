package com.lashmanager.app.domain.exception;

public class ClientAlreadyExistsException extends DomainException {
    public ClientAlreadyExistsException(String phone) {
        super("Já existe um cliente com o telefone: " + phone);
    }
}
