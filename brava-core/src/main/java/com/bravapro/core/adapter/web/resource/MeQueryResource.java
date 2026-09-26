package com.bravapro.core.adapter.web.resource;

import com.bravapro.core.domain.model.CurrentUserDetails;
import com.bravapro.core.domain.port.in.MeQueryService;
import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class MeQueryResource implements QueryPermissionAware {

    private final MeQueryService meQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.authenticated();
    }

    @GetMapping
    public ResponseEntity<CurrentUserDetails> current() {
        return ResponseEntity.ok(meQueryService.current());
    }
}
