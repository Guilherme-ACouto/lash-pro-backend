package com.bravapro.core.adapter.web.resource;

import com.bravapro.core.application.command.AcceptInviteCommand;
import com.bravapro.core.application.service.InvitationApplicationService;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.infrastructure.web.RestUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Público ({@code /api/invitation/**} liberado no SecurityConfig). Leitura em {@link InvitationQueryResource}. */
@RestController
@RequestMapping("/api/invitation")
@RequiredArgsConstructor
public class InvitationResource {

    private static final String ENTITY_NAME = "invite";

    private final InvitationApplicationService invitationApplicationService;

    @PostMapping("/accept")
    public ResponseEntity<Void> accept(@Valid @RequestBody AcceptInviteCommand command) {
        User user = invitationApplicationService.when(command);
        return RestUtils.message().updated(ENTITY_NAME, user.getId());
    }
}
