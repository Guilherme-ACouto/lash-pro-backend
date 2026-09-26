package com.bravapro.core.domain.exception;

public class PasswordResetTokenInvalidException extends DomainException {
    public PasswordResetTokenInvalidException() {
        super("Link de redefinição de senha inválido ou expirado — peça um novo");
    }
}
