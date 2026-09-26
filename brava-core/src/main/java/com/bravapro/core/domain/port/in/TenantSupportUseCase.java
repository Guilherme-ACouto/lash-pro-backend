package com.bravapro.core.domain.port.in;

import com.bravapro.core.domain.model.SupportSession;
import com.bravapro.core.domain.model.Tenant;

public interface TenantSupportUseCase {

    SupportSession enter(Tenant tenant);
}
