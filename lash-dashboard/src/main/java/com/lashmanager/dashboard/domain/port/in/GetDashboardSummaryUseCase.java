package com.lashmanager.dashboard.domain.port.in;

import java.math.BigDecimal;
import java.util.List;

public interface GetDashboardSummaryUseCase {

    record MonthlyRevenueStat(int year, int month, BigDecimal revenue, BigDecimal expense) {}

    record DashboardSummary(
            long totalClients,
            long newClientsThisMonth,
            long appointmentsToday,
            long appointmentsThisWeek,
            long appointmentsThisMonth,
            BigDecimal revenueThisMonth,
            BigDecimal expenseThisMonth,
            long lowStockItems,
            long outOfStockItems,
            List<MonthlyRevenueStat> last6Months) {}

    DashboardSummary execute();
}
