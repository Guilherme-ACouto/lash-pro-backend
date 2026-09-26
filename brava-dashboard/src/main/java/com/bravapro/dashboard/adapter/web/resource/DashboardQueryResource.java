package com.bravapro.dashboard.adapter.web.resource;

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
public class DashboardQueryResource {

    private final DashboardQueryService dashboardQueryService;

    @GetMapping
    public ResponseEntity<DashboardData> get(@RequestParam DashboardPeriod period) {
        return ResponseEntity.ok(dashboardQueryService.getDashboard(period));
    }
}
