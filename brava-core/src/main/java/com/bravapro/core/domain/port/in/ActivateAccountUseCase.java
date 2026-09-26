package com.bravapro.core.domain.port.in;

import java.util.UUID;

public interface ActivateAccountUseCase {

    record ActivationResult(String email, UUID tenantId) {}

    ActivationResult execute(String activationKey);
}
