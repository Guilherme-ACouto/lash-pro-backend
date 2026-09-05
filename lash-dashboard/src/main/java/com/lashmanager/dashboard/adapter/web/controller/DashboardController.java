package com.lashmanager.dashboard.adapter.web.controller;

import com.lashmanager.dashboard.domain.port.in.GetDashboardSummaryUseCase;
import com.lashmanager.dashboard.domain.port.in.GetTodayScheduleUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class DashboardController {

    private final GetDashboardSummaryUseCase getDashboardSummaryUseCase;
    private final GetTodayScheduleUseCase getTodayScheduleUseCase;

    @GetMapping("/summary")
    public ResponseEntity<GetDashboardSummaryUseCase.DashboardSummary> summary() {
        return ResponseEntity.ok(getDashboardSummaryUseCase.execute());
    }

    @GetMapping("/today")
    public ResponseEntity<List<GetTodayScheduleUseCase.ScheduleEntry>> today() {
        return ResponseEntity.ok(getTodayScheduleUseCase.execute());
    }
}
