package com.bravapro.settings.domain.port.in;

import com.bravapro.settings.domain.port.out.BusinessUnitLogoStorage;

import java.util.Optional;
import java.util.UUID;

public interface LogoQueryService {

    Optional<BusinessUnitLogoStorage.LogoFile> load(UUID tenantId, String fileName);
}
