package com.bravapro.core.domain.exception;

public class AccountNotActivatedException extends DomainException {
    public AccountNotActivatedException() {
        super("Confirme seu cadastro pelo e-mail antes de entrar");
    }
}
