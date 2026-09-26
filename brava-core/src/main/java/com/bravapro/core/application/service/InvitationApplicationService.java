package com.bravapro.core.application.service;

import com.bravapro.core.application.command.AcceptInviteCommand;
import com.bravapro.core.domain.exception.InviteExpiredException;
import com.bravapro.core.domain.exception.InviteInvalidException;
import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.in.InvitationUseCase;
import com.bravapro.core.domain.port.out.TenantInviteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Aceite público de convite: a busca é pelo token (igual ao fluxo público de anamnese). */
@Service
@RequiredArgsConstructor
public class InvitationApplicationService {

    private final InvitationUseCase invitationUseCase;
    private final TenantInviteRepository tenantInviteRepository;

    public User when(AcceptInviteCommand command) {
        TenantInvite invite = tenantInviteRepository
                .findByToken(command.getToken())
                .filter(TenantInvite::isPending)
                .orElseThrow(InviteInvalidException::new);
        if (invite.isExpired()) {
            throw new InviteExpiredException();
        }
        return invitationUseCase.accept(invite, command);
    }
}
