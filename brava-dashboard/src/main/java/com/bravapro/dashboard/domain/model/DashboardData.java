package com.bravapro.dashboard.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record DashboardData(
        long activeClients,
        double clientsGrowth,
        long totalAppointments,
        long completedAppointments,
        long confirmedAppointments,
        long scheduledAppointments,
        long cancellations,
        BigDecimal revenue,
        BigDecimal receivable,
        BigDecimal payable,
        List<AppointmentDayStat> appointmentsSeries,
        List<CashFlowDayStat> cashFlowSeries,
        List<TodayAppointmentEntry> todayAppointments,
        List<String> daysWithAppointments) {}
