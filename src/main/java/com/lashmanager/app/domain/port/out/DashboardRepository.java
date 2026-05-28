package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.dashboard.AppointmentCounts;
import com.lashmanager.app.domain.model.dashboard.AppointmentDayStat;
import com.lashmanager.app.domain.model.dashboard.CashFlowDayStat;
import com.lashmanager.app.domain.model.dashboard.TodayAppointmentStat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DashboardRepository {
    long countActiveClients();
    long countNewClientsInPeriod(LocalDate start, LocalDate end);
    AppointmentCounts countAppointmentsByPeriod(LocalDate start, LocalDate end);
    List<AppointmentDayStat> appointmentsSeriesByPeriod(LocalDate start, LocalDate end);
    BigDecimal sumRevenue(LocalDate start, LocalDate end);
    BigDecimal sumReceivable();
    BigDecimal sumPayable();
    List<CashFlowDayStat> cashFlowLast7Days();
    List<TodayAppointmentStat> todayAppointments(LocalDate today);
    List<LocalDate> daysWithAppointmentsInMonth(LocalDate monthStart, LocalDate monthEnd);
}
