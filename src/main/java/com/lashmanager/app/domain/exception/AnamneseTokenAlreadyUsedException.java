package com.lashmanager.app.domain.exception;

public class AnamneseTokenAlreadyUsedException extends DomainException {
    public AnamneseTokenAlreadyUsedException() {
        super("Esta ficha já foi preenchida.");
    }
}
