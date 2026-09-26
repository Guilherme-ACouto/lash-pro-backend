package com.bravapro.settings.domain.port.in;

import com.bravapro.core.domain.model.User;
import com.bravapro.settings.application.command.UpdateTeamUserCommand;

public interface TeamUserUseCase {

    void update(User user, UpdateTeamUserCommand command);

    void deactivate(User user);

    void reactivate(User user);

    void delete(User user);

    void resetPassword(User user);

    void endSessions(User user);
}
