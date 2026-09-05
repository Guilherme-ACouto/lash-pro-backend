package com.lashmanager.core.domain.port.in;

import java.util.UUID;

public interface DeactivateTenantUseCase {
    void execute(UUID tenantId);
}
