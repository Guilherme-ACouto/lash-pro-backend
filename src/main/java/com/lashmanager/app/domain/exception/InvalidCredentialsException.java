package com.lashmanager.app.domain.exception;

public class InvalidCredentialsException extends DomainException {
    public InvalidCredentialsException() {
        super("Credenciais inválidas");
    }
}
