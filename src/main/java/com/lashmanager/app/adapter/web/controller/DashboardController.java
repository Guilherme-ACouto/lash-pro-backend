package com.lashmanager.app.adapter.web.controller;

import com.lashmanager.app.adapter.web.dto.DashboardResponse;
import com.lashmanager.app.domain.port.in.GetDashboardUseCase;
import com.lashmanager.app.domain.port.in.GetDashboardUseCase.DashboardResult;
import com.lashmanager.app.domain.port.in.GetDashboardUseCase.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final GetDashboardUseCase getDashboardUseCase;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(
            @RequestParam(defaultValue = "WEEK") String period) {

        Period p = switch (period.toUpperCase()) {
            case "TODAY" -> Period.TODAY;
            case "MONTH" -> Period.MONTH;
            default -> Period.WEEK;
        };

        DashboardResult result = getDashboardUseCase.execute(p);
        return ResponseEntity.ok(toResponse(result));
    }

    private DashboardResponse toResponse(DashboardResult r) {
        List<DashboardResponse.AppointmentDayStatDTO> appointmentsSeries = r.appointmentsSeries().stream()
                .map(s -> new DashboardResponse.AppointmentDayStatDTO(
                        s.date().toString(), s.completed(), s.confirmed(), s.scheduled(), s.cancelled()))
                .toList();

        List<DashboardResponse.CashFlowDayStatDTO> cashFlowSeries = r.cashFlowSeries().stream()
                .map(s -> new DashboardResponse.CashFlowDayStatDTO(
                        s.date().toString(), s.income(), s.expense()))
                .toList();

        List<DashboardResponse.TodayAppointmentDTO> todayAppointments = r.todayAppointments().stream()
                .map(a -> new DashboardResponse.TodayAppointmentDTO(
                        a.id(), a.clientName(), a.serviceName(), a.scheduledTime(), a.status()))
                .toList();

        List<String> daysWithAppointments = r.daysWithAppointments().stream()
                .map(Object::toString)
                .toList();

        return new DashboardResponse(
                r.activeClients(),
                r.clientsGrowth(),
                r.totalAppointments(),
                r.completedAppointments(),
                r.confirmedAppointments(),
                r.scheduledAppointments(),
                r.cancellations(),
                r.revenue(),
                r.receivable(),
                r.payable(),
                appointmentsSeries,
                cashFlowSeries,
                todayAppointments,
                daysWithAppointments
        );
    }
}
