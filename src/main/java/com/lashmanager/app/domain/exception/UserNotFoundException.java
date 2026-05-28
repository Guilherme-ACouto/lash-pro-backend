package com.lashmanager.app.domain.exception;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
    public UserNotFoundException(String email) {
        super("Usuário não encontrado: " + email);
    }
}
