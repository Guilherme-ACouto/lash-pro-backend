package com.bravapro.core.domain.exception;

public class InviteExpiredException extends DomainException {
    public InviteExpiredException() {
        super("Este convite expirou — peça a um administrador da assinatura para reenviar");
    }
}
