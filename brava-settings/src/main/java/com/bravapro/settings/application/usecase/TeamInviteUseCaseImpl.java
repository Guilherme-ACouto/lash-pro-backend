package com.bravapro.settings.application.usecase;

import com.bravapro.core.domain.exception.BusinessException;
import com.bravapro.core.domain.exception.EmailAlreadyInUseException;
import com.bravapro.core.domain.model.Tenant;
import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.domain.port.out.EmailPort;
import com.bravapro.core.domain.port.out.TenantInviteRepository;
import com.bravapro.core.domain.port.out.TenantRepository;
import com.bravapro.core.domain.port.out.UserRepository;
import com.bravapro.settings.application.command.InviteUserCommand;
import com.bravapro.settings.domain.model.PermissionKeys;
import com.bravapro.settings.domain.port.in.TeamInviteUseCase;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamInviteUseCaseImpl implements TeamInviteUseCase {

    private final TenantInviteRepository tenantInviteRepository;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final EmailPort emailPort;
    private final CurrentAccess currentAccess;

    @Value("${app.invite.expiration-hours:48}")
    private long inviteExpirationHours;

    @Override
    public TenantInvite invite(InviteUserCommand command) {
        String email = command.getEmail().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException(email);
        }
        UUID tenantId = currentAccess.tenantId();
        if (tenantInviteRepository.findPendingByTenantAndEmail(tenantId, email).isPresent()) {
            throw new BusinessException("Já existe um convite pendente para este e-mail — use \"Reenviar convite\"");
        }

        LocalDateTime now = LocalDateTime.now();
        TenantInvite invite = tenantInviteRepository.save(TenantInvite.builder()
                .id(UUID.randomUUID())
                .tenantId(tenantId)
                .email(email)
                .name(command.getName().trim())
                .admin(command.isAdmin())
                .professional(command.isProfessional())
                .permissions(PermissionKeys.parse(command.getPermissions()))
                .token(newToken())
                .status(TenantInvite.STATUS_PENDING)
                .expiresAt(now.plusHours(inviteExpirationHours))
                .invitedBy(currentAccess.userId())
                .createdAt(now)
                .build());

        sendEmail(invite);
        return invite;
    }

    @Override
    public void resend(TenantInvite invite) {
        invite.renew(newToken(), LocalDateTime.now().plusHours(inviteExpirationHours));
        tenantInviteRepository.save(invite);
        sendEmail(invite);
    }

    @Override
    public void cancel(TenantInvite invite) {
        invite.assertPending();
        tenantInviteRepository.deleteById(invite.getId());
    }

    private void sendEmail(TenantInvite invite) {
        String tenantName = tenantRepository.findById(invite.getTenantId()).map(Tenant::getName).orElse("Brava Pro");
        emailPort.sendInviteEmail(invite.getEmail(), invite.getName(), tenantName, invite.getToken(), inviteExpirationHours);
    }

    private static String newToken() {
        return UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "");
    }
}
