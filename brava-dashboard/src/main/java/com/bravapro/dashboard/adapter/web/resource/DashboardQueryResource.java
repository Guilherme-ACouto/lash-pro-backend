package com.bravapro.dashboard.adapter.web.resource;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.dashboard.domain.model.DashboardData;
import com.bravapro.dashboard.domain.model.DashboardPeriod;
import com.bravapro.dashboard.domain.port.in.DashboardQueryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Módulo 100% leitura — sem Resource de comando (nada pra separar). */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class DashboardQueryResource implements QueryPermissionAware {

    private final DashboardQueryService dashboardQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.of(Permission.DASHBOARD);
    }

    @GetMapping
    public ResponseEntity<DashboardData> get(@RequestParam DashboardPeriod period) {
        return ResponseEntity.ok(dashboardQueryService.getDashboard(period));
    }
}
