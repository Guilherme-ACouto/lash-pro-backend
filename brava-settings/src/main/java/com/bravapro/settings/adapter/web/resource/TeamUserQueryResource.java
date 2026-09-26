package com.bravapro.settings.adapter.web.resource;

import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.settings.domain.model.TeamUser;
import com.bravapro.settings.domain.port.in.TeamUserQueryService;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Só leitura — comando em {@link TeamUserResource}. Devolve {@link TeamUser} (composição User +
 * Collaborator feita em application/query, então tipo de domínio e não Response).
 */
@RestController
@RequestMapping("/api/settings/users")
@RequiredArgsConstructor
public class TeamUserQueryResource implements QueryPermissionAware {

    private final TeamUserQueryService teamUserQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.admin();
    }

    @GetMapping
    public ResponseEntity<List<TeamUser>> list() {
        return ResponseEntity.ok(teamUserQueryService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamUser> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(teamUserQueryService.getById(id));
    }
}
