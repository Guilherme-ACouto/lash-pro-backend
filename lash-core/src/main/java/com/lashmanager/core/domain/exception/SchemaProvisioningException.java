package com.lashmanager.core.domain.exception;

import java.util.UUID;

public class SchemaProvisioningException extends DomainException {
    public SchemaProvisioningException(UUID tenantId, Throwable cause) {
        super("Falha ao provisionar schema do tenant " + tenantId + ": " + cause.getMessage());
        initCause(cause);
    }
}
