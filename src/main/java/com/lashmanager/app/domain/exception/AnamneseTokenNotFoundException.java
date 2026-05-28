package com.lashmanager.app.domain.exception;

public class AnamneseTokenNotFoundException extends DomainException {
    public AnamneseTokenNotFoundException() {
        super("Link inválido ou expirado");
    }
}
