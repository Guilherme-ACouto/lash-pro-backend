package com.bravapro.core.domain.exception;

public class TenantNotFoundException extends DomainException {
    public TenantNotFoundException() {
        super("Tenant não encontrado");
    }
}
