package com.bravapro.settings.domain.port.out;

import com.bravapro.settings.domain.model.BusinessUnit;

import java.util.Optional;

public interface BusinessUnitQueryRepository {

    Optional<BusinessUnit> findMain();
}
