package com.bravapro.settings.application.service;

import com.bravapro.core.domain.exception.InviteInvalidException;
import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.domain.port.out.TenantInviteRepository;
import com.bravapro.settings.application.command.CancelInviteCommand;
import com.bravapro.settings.application.command.InviteUserCommand;
import com.bravapro.settings.application.command.ResendInviteCommand;
import com.bravapro.settings.domain.port.in.TeamInviteUseCase;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamInviteApplicationService {

    private final TeamInviteUseCase teamInviteUseCase;
    private final TenantInviteRepository tenantInviteRepository;
    private final CurrentAccess currentAccess;

    public TenantInvite when(InviteUserCommand command) {
        return teamInviteUseCase.invite(command);
    }

    public void when(ResendInviteCommand command) {
        teamInviteUseCase.resend(getOne(command.getId()));
    }

    public void when(CancelInviteCommand command) {
        teamInviteUseCase.cancel(getOne(command.getId()));
    }

    private TenantInvite getOne(UUID id) {
        return tenantInviteRepository
                .findById(id)
                .filter(invite -> invite.getTenantId().equals(currentAccess.tenantId()))
                .filter(TenantInvite::isPending)
                .orElseThrow(InviteInvalidException::new);
    }
}
