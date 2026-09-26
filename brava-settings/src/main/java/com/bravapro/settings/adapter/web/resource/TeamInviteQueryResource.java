package com.bravapro.settings.adapter.web.resource;

import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.settings.adapter.web.dto.TeamInviteResponse;
import com.bravapro.settings.domain.port.in.TeamInviteQueryService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Só leitura — comando em {@link TeamInviteResource} (mesma URL base). */
@RestController
@RequestMapping("/api/settings/invites")
@RequiredArgsConstructor
public class TeamInviteQueryResource implements QueryPermissionAware {

    private final TeamInviteQueryService teamInviteQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.admin();
    }

    @GetMapping
    public ResponseEntity<List<TeamInviteResponse>> listPending() {
        return ResponseEntity.ok(teamInviteQueryService.listPending().stream()
                .map(TeamInviteResponse::from)
                .toList());
    }
}
