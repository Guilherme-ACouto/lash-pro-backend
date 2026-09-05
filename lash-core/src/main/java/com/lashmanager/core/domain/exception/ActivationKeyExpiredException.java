package com.lashmanager.core.domain.exception;

public class ActivationKeyExpiredException extends DomainException {
    public ActivationKeyExpiredException() {
        super("Link de ativação expirado — solicite um novo cadastro/reenvio");
    }
}
