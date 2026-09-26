package com.bravapro.core.domain.exception;

public class TenantInactiveException extends DomainException {
    public TenantInactiveException() {
        super("Sua conta está desativada — contate o suporte");
    }
}
