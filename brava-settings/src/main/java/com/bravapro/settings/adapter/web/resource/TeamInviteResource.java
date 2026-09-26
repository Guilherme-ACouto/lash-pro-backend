package com.bravapro.settings.adapter.web.resource;

import com.bravapro.core.domain.model.TenantInvite;
import com.bravapro.core.infrastructure.web.RestUtils;
import com.bravapro.settings.application.command.CancelInviteCommand;
import com.bravapro.settings.application.command.InviteUserCommand;
import com.bravapro.settings.application.command.ResendInviteCommand;
import com.bravapro.settings.application.service.TeamInviteApplicationService;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só comando — leitura em {@link TeamInviteQueryResource}. */
@RestController
@RequestMapping("/api/settings/invites")
@RequiredArgsConstructor
public class TeamInviteResource {

    private static final String ENTITY_NAME = "teamInvite";

    private final TeamInviteApplicationService teamInviteApplicationService;

    @PostMapping
    public ResponseEntity<Object> invite(@Valid @RequestBody InviteUserCommand command) {
        TenantInvite invite = teamInviteApplicationService.when(command);
        return RestUtils.message().created(ENTITY_NAME, invite);
    }

    @PostMapping("/{id}/resend")
    public ResponseEntity<Void> resend(@PathVariable UUID id) {
        teamInviteApplicationService.when(new ResendInviteCommand(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        teamInviteApplicationService.when(new CancelInviteCommand(id));
        return RestUtils.message().deleted(ENTITY_NAME, id);
    }
}
