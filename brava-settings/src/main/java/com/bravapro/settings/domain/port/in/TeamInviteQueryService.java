package com.bravapro.settings.domain.port.in;

import com.bravapro.core.domain.model.TenantInvite;

import java.util.List;

public interface TeamInviteQueryService {

    /** Convites ainda não aceitos da assinatura atual. */
    List<TenantInvite> listPending();
}
