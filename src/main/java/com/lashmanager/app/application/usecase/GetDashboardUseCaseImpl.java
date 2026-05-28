package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.dashboard.AppointmentCounts;
import com.lashmanager.app.domain.port.in.GetDashboardUseCase;
import com.lashmanager.app.domain.port.out.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GetDashboardUseCaseImpl implements GetDashboardUseCase {

    private final DashboardRepository dashboardRepository;

    @Override
    public DashboardResult execute(Period period) {
        LocalDate today = LocalDate.now();
        LocalDate start = resolveStart(period, today);
        LocalDate end = today;

        LocalDate currentMonthStart = today.withDayOfMonth(1);
        LocalDate prevMonthStart = currentMonthStart.minusMonths(1);

        long currentNew = dashboardRepository.countNewClientsInPeriod(currentMonthStart, today.plusDays(1));
        long prevNew = dashboardRepository.countNewClientsInPeriod(prevMonthStart, currentMonthStart);

        double growth = prevNew == 0 ? 0.0 : (currentNew - prevNew) * 100.0 / prevNew;

        AppointmentCounts counts = dashboardRepository.countAppointmentsByPeriod(start, end);

        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

        return new DashboardResult(
                dashboardRepository.countActiveClients(),
                Math.round(growth * 10.0) / 10.0,
                counts.total(),
                counts.completed(),
                counts.confirmed(),
                counts.scheduled(),
                counts.cancelled(),
                dashboardRepository.sumRevenue(start, end),
                dashboardRepository.sumReceivable(),
                dashboardRepository.sumPayable(),
                dashboardRepository.appointmentsSeriesByPeriod(start, end),
                dashboardRepository.cashFlowLast7Days(),
                dashboardRepository.todayAppointments(today),
                dashboardRepository.daysWithAppointmentsInMonth(monthStart, monthEnd)
        );
    }

    private LocalDate resolveStart(Period period, LocalDate today) {
        return switch (period) {
            case TODAY -> today;
            case WEEK -> today.minusDays(6);
            case MONTH -> today.withDayOfMonth(1);
        };
    }
}
