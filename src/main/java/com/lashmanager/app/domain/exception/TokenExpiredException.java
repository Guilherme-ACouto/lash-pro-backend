package com.lashmanager.app.domain.exception;

public class TokenExpiredException extends DomainException {
    public TokenExpiredException() {
        super("Token expirado ou inválido");
    }
}
