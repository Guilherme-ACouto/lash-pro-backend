package com.bravapro.settings.application.service;

import com.bravapro.core.domain.exception.UserNotFoundException;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.domain.port.out.UserRepository;
import com.bravapro.settings.application.command.DeactivateTeamUserCommand;
import com.bravapro.settings.application.command.DeleteTeamUserCommand;
import com.bravapro.settings.application.command.EndTeamUserSessionsCommand;
import com.bravapro.settings.application.command.ReactivateTeamUserCommand;
import com.bravapro.settings.application.command.ResetTeamUserPasswordCommand;
import com.bravapro.settings.application.command.UpdateTeamUserCommand;
import com.bravapro.settings.domain.port.in.TeamUserUseCase;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Usuários ficam no public (compartilhado entre assinaturas): a busca só aceita quem pertence à
 * assinatura atual — de outra, responde como se não existisse.
 */
@Service
@RequiredArgsConstructor
public class TeamUserApplicationService {

    private final TeamUserUseCase teamUserUseCase;
    private final UserRepository userRepository;
    private final CurrentAccess currentAccess;

    public void when(UpdateTeamUserCommand command) {
        teamUserUseCase.update(getOne(command.getId()), command);
    }

    public void when(DeactivateTeamUserCommand command) {
        teamUserUseCase.deactivate(getOne(command.getId()));
    }

    public void when(ReactivateTeamUserCommand command) {
        teamUserUseCase.reactivate(getOne(command.getId()));
    }

    public void when(DeleteTeamUserCommand command) {
        teamUserUseCase.delete(getOne(command.getId()));
    }

    public void when(ResetTeamUserPasswordCommand command) {
        teamUserUseCase.resetPassword(getOne(command.getId()));
    }

    public void when(EndTeamUserSessionsCommand command) {
        teamUserUseCase.endSessions(getOne(command.getId()));
    }

    private User getOne(UUID id) {
        return userRepository
                .findById(id)
                .filter(user -> user.belongsTo(currentAccess.tenantId()))
                .orElseThrow(UserNotFoundException::new);
    }
}
