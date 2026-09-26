package com.bravapro.core.domain.port.in;

import com.bravapro.core.application.command.AcceptInviteCommand;
import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.domain.model.User;

public interface InvitationUseCase {

    /** Cria a conta e o acesso à assinatura a partir de um convite pendente e válido. */
    User accept(TenantInvite invite, AcceptInviteCommand command);
}
