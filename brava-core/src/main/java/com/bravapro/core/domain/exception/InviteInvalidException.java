package com.bravapro.core.domain.exception;

public class InviteInvalidException extends DomainException {
    public InviteInvalidException() {
        super("Convite inválido ou já utilizado");
    }
}
