package com.bravapro.settings.domain.port.in;

import com.bravapro.settings.domain.model.BusinessUnit;

public interface BusinessUnitQueryService {

    /** Unidade principal da assinatura atual. */
    BusinessUnit getMain();
}
