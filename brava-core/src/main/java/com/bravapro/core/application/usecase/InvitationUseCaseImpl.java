package com.bravapro.core.application.usecase;

import com.bravapro.core.application.command.AcceptInviteCommand;
import com.bravapro.core.domain.exception.EmailAlreadyInUseException;
import com.bravapro.core.domain.model.Collaborator;
import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.in.InvitationUseCase;
import com.bravapro.core.domain.port.out.CollaboratorRepository;
import com.bravapro.core.domain.port.out.TenantInviteRepository;
import com.bravapro.core.domain.port.out.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationUseCaseImpl implements InvitationUseCase {

    private final UserRepository userRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final TenantInviteRepository tenantInviteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User accept(TenantInvite invite, AcceptInviteCommand command) {
        if (userRepository.existsByEmail(invite.getEmail())) {
            throw new EmailAlreadyInUseException(invite.getEmail());
        }

        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.save(User.builder()
                .id(UUID.randomUUID())
                .name(command.getName().trim())
                .email(invite.getEmail())
                .password(passwordEncoder.encode(command.getPassword()))
                .admin(invite.isAdmin())
                .active(true)
                .tenantId(invite.getTenantId())
                .createdAt(now)
                .updatedAt(now)
                .build());

        collaboratorRepository.save(
                invite.getTenantId(),
                Collaborator.of(user.getId(), invite.isProfessional(), invite.getPermissions()));

        invite.confirm();
        tenantInviteRepository.save(invite);

        if (log.isInfoEnabled()) {
            log.info("Convite aceito: {} entrou no tenant {}", user.getEmail(), invite.getTenantId());
        }
        return user;
    }
}
