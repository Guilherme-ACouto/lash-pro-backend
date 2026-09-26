package com.bravapro.settings.adapter.web.resource;

import com.bravapro.core.infrastructure.web.RestUtils;
import com.bravapro.settings.application.command.DeactivateTeamUserCommand;
import com.bravapro.settings.application.command.DeleteTeamUserCommand;
import com.bravapro.settings.application.command.EndTeamUserSessionsCommand;
import com.bravapro.settings.application.command.ReactivateTeamUserCommand;
import com.bravapro.settings.application.command.ResetTeamUserPasswordCommand;
import com.bravapro.settings.application.command.UpdateTeamUserCommand;
import com.bravapro.settings.application.service.TeamUserApplicationService;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Só comando — leitura em {@link TeamUserQueryResource}. Usuário novo não é criado aqui: entra por
 * convite ({@link TeamInviteResource}).
 */
@RestController
@RequestMapping("/api/settings/users")
@RequiredArgsConstructor
public class TeamUserResource {

    private static final String ENTITY_NAME = "teamUser";

    private final TeamUserApplicationService teamUserApplicationService;

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateTeamUserCommand command) {
        teamUserApplicationService.when(command.id(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        teamUserApplicationService.when(new DeactivateTeamUserCommand(id));
        return RestUtils.message().deactivated(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        teamUserApplicationService.when(new ReactivateTeamUserCommand(id));
        return RestUtils.message().reactivated(ENTITY_NAME, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        teamUserApplicationService.when(new DeleteTeamUserCommand(id));
        return RestUtils.message().deleted(ENTITY_NAME, id);
    }

    @PostMapping("/{id}/reset-password")
    public ResponseEntity<Void> resetPassword(@PathVariable UUID id) {
        teamUserApplicationService.when(new ResetTeamUserPasswordCommand(id));
        return RestUtils.message().updated("teamUserPasswordReset", id);
    }

    @PostMapping("/{id}/end-sessions")
    public ResponseEntity<Void> endSessions(@PathVariable UUID id) {
        teamUserApplicationService.when(new EndTeamUserSessionsCommand(id));
        return RestUtils.message().updated("teamUserSessions", id);
    }
}
