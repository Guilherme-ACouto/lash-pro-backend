package com.bravapro.core.domain.exception;

public class InvalidCredentialsException extends DomainException {
    public InvalidCredentialsException() {
        super("Credenciais inválidas");
    }
}
