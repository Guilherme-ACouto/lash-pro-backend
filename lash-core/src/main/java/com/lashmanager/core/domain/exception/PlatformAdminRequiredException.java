package com.lashmanager.core.domain.exception;

public class PlatformAdminRequiredException extends DomainException {
    public PlatformAdminRequiredException() {
        super("Operação restrita a usuários de plataforma");
    }
}
