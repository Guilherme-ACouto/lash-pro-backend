package com.bravapro.settings.domain.port.out;

import com.bravapro.settings.domain.model.BusinessUnit;

import java.util.Optional;
import java.util.UUID;

public interface BusinessUnitRepository {

    BusinessUnit save(BusinessUnit businessUnit);

    Optional<BusinessUnit> findById(UUID id);
}
