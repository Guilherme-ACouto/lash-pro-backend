package com.bravapro.core.domain.port.in;

import com.bravapro.core.domain.model.CurrentUserDetails;

public interface MeQueryService {

    CurrentUserDetails current();
}
