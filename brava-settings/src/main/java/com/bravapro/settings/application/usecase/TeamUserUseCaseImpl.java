package com.bravapro.settings.application.usecase;

import com.bravapro.core.domain.exception.BusinessException;
import com.bravapro.core.domain.exception.TenantNotFoundException;
import com.bravapro.core.domain.model.Collaborator;
import com.bravapro.core.domain.model.Tenant;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.in.ForgotPasswordUseCase;
import com.bravapro.core.domain.port.out.CollaboratorRepository;
import com.bravapro.core.domain.port.out.CommandAuditLogRepository;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.domain.port.out.TenantRepository;
import com.bravapro.core.domain.port.out.UserRepository;
import com.bravapro.settings.application.command.UpdateTeamUserCommand;
import com.bravapro.settings.domain.model.PermissionKeys;
import com.bravapro.settings.domain.port.in.TeamUserUseCase;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Regras de administração de usuários da assinatura:
 * <ul>
 *   <li>a titular da assinatura é sempre administradora e não pode ser inativada nem excluída;</li>
 *   <li>ninguém tira o próprio acesso de administrador nem se inativa/exclui;</li>
 *   <li>a assinatura nunca fica sem nenhum administrador ativo;</li>
 *   <li>excluir de vez só quem nunca executou nada — quem tem histórico é inativado.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class TeamUserUseCaseImpl implements TeamUserUseCase {

    private final UserRepository userRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final TenantRepository tenantRepository;
    private final CommandAuditLogRepository commandAuditLogRepository;
    private final CurrentAccess currentAccess;
    private final ForgotPasswordUseCase forgotPasswordUseCase;

    @Override
    @Transactional
    public void update(User user, UpdateTeamUserCommand command) {
        boolean losingAdmin = user.isAdmin() && !command.isAdmin();
        if (losingAdmin) {
            if (currentTenant().isOwner(user.getId())) {
                throw new BusinessException("A titular da assinatura sempre tem acesso de administrador");
            }
            if (isSelf(user)) {
                throw new BusinessException("Você não pode remover o seu próprio acesso de administrador");
            }
            assertNotLastActiveAdmin(user);
        }

        user.update(command.getName().trim(), command.isAdmin());
        userRepository.save(user);

        UUID tenantId = currentAccess.tenantId();
        Collaborator collaborator = collaboratorRepository
                .findByUserId(tenantId, user.getId())
                .orElseGet(() -> Collaborator.of(user.getId(), false, null));
        collaborator.update(command.isProfessional(), PermissionKeys.parse(command.getPermissions()));
        collaboratorRepository.save(tenantId, collaborator);
    }

    @Override
    public void deactivate(User user) {
        if (currentTenant().isOwner(user.getId())) {
            throw new BusinessException("A titular da assinatura não pode ser inativada");
        }
        if (isSelf(user)) {
            throw new BusinessException("Você não pode inativar o seu próprio usuário");
        }
        if (user.isAdmin()) {
            assertNotLastActiveAdmin(user);
        }
        user.deactivate();
        userRepository.save(user);
    }

    @Override
    public void reactivate(User user) {
        user.reactivate();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        if (currentTenant().isOwner(user.getId())) {
            throw new BusinessException("A titular da assinatura não pode ser excluída");
        }
        if (isSelf(user)) {
            throw new BusinessException("Você não pode excluir o seu próprio usuário");
        }
        if (commandAuditLogRepository.existsSuccessfulByUserId(user.getEmail())) {
            throw new BusinessException("Este usuário já tem histórico no sistema — inative em vez de excluir");
        }
        if (user.isAdmin() && user.isActive()) {
            assertNotLastActiveAdmin(user);
        }
        collaboratorRepository.delete(currentAccess.tenantId(), user.getId());
        userRepository.deleteById(user.getId());
    }

    @Override
    public void resetPassword(User user) {
        if (!user.isActive()) {
            throw new BusinessException("Reative o usuário antes de redefinir a senha");
        }
        forgotPasswordUseCase.execute(user.getEmail());
    }

    @Override
    public void endSessions(User user) {
        user.invalidateSessions();
        userRepository.save(user);
    }

    private void assertNotLastActiveAdmin(User user) {
        if (user.isActive() && userRepository.countActiveAdmins(currentAccess.tenantId()) <= 1) {
            throw new BusinessException("A assinatura precisa de pelo menos um administrador ativo");
        }
    }

    private boolean isSelf(User user) {
        return user.getId().equals(currentAccess.userId());
    }

    private Tenant currentTenant() {
        return tenantRepository.findById(currentAccess.tenantId()).orElseThrow(TenantNotFoundException::new);
    }
}
