package com.bravapro.core.application.query;

import com.bravapro.core.domain.exception.InviteInvalidException;
import com.bravapro.core.domain.model.InvitationDetails;
import com.bravapro.core.domain.model.Tenant;
import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.domain.port.in.InvitationQueryService;
import com.bravapro.core.domain.port.out.TenantInviteRepository;
import com.bravapro.core.domain.port.out.TenantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationQueryServiceImpl implements InvitationQueryService {

    private final TenantInviteRepository tenantInviteRepository;
    private final TenantRepository tenantRepository;

    @Override
    public InvitationDetails getByToken(String token) {
        TenantInvite invite = tenantInviteRepository
                .findByToken(token)
                .filter(TenantInvite::isPending)
                .orElseThrow(InviteInvalidException::new);
        String tenantName = tenantRepository.findById(invite.getTenantId()).map(Tenant::getName).orElse("");
        return new InvitationDetails(invite.getName(), invite.getEmail(), tenantName, invite.isExpired());
    }
}
