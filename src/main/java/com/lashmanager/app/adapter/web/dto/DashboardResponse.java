package com.lashmanager.app.adapter.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
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
        List<AppointmentDayStatDTO> appointmentsSeries,
        List<CashFlowDayStatDTO> cashFlowSeries,
        List<TodayAppointmentDTO> todayAppointments,
        List<String> daysWithAppointments
) {

    public record AppointmentDayStatDTO(
            String date,
            long completed,
            long confirmed,
            long scheduled,
            long cancelled
    ) {}

    public record CashFlowDayStatDTO(
            String date,
            BigDecimal income,
            BigDecimal expense
    ) {}

    public record TodayAppointmentDTO(
            String id,
            String clientName,
            String serviceName,
            String scheduledTime,
            String status
    ) {}
}
