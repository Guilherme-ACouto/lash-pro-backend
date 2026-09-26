package com.bravapro.core.domain.exception;

public class PermissionDeniedException extends DomainException {
    public PermissionDeniedException() {
        super("Você não tem permissão para realizar esta ação");
    }

    public PermissionDeniedException(String message) {
        super(message);
    }
}
