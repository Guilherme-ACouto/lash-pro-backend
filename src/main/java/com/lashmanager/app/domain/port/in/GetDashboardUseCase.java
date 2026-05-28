package com.lashmanager.app.domain.port.in;

import com.lashmanager.app.domain.model.dashboard.AppointmentDayStat;
import com.lashmanager.app.domain.model.dashboard.CashFlowDayStat;
import com.lashmanager.app.domain.model.dashboard.TodayAppointmentStat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GetDashboardUseCase {

    enum Period { TODAY, WEEK, MONTH }

    record DashboardResult(
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
            List<TodayAppointmentStat> todayAppointments,
            List<LocalDate> daysWithAppointments
    ) {}

    DashboardResult execute(Period period);
}
