package com.bravapro.settings.application.query;

import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.domain.port.out.TenantInviteRepository;
import com.bravapro.settings.domain.port.in.TeamInviteQueryService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamInviteQueryServiceImpl implements TeamInviteQueryService {

    private final TenantInviteRepository tenantInviteRepository;
    private final CurrentAccess currentAccess;

    @Override
    public List<TenantInvite> listPending() {
        return tenantInviteRepository.findPendingByTenant(currentAccess.tenantId());
    }
}
