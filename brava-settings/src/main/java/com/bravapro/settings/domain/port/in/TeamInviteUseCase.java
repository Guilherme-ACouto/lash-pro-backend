package com.bravapro.settings.domain.port.in;

import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.settings.application.command.InviteUserCommand;

public interface TeamInviteUseCase {

    TenantInvite invite(InviteUserCommand command);

    void resend(TenantInvite invite);

    void cancel(TenantInvite invite);
}
