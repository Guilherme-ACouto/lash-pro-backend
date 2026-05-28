package com.lashmanager.app.domain.exception;

public class AnamneseTokenExpiredException extends DomainException {
    public AnamneseTokenExpiredException() {
        super("Este link expirou. Peça um novo à profissional.");
    }
}
