package com.lashmanager.dashboard.application.usecase;

import com.lashmanager.appointments.infrastructure.persistence.repository.AppointmentJpaRepository;
import com.lashmanager.clients.infrastructure.persistence.repository.ClientJpaRepository;
import com.lashmanager.dashboard.domain.port.in.GetDashboardSummaryUseCase;
import com.lashmanager.finance.infrastructure.persistence.repository.FinancialEntryJpaRepository;
import com.lashmanager.stock.infrastructure.persistence.repository.InventoryItemJpaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDashboardSummaryUseCaseImpl implements GetDashboardSummaryUseCase {

  private final ClientJpaRepository clientJpaRepository;
  private final AppointmentJpaRepository appointmentJpaRepository;
  private final FinancialEntryJpaRepository financialEntryJpaRepository;
  private final InventoryItemJpaRepository inventoryItemJpaRepository;

  @Override
  public DashboardSummary execute() {
    LocalDate today = LocalDate.now();
    LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
    LocalDate endOfWeek = today.with(java.time.DayOfWeek.SUNDAY);
    LocalDate startOfMonth = today.withDayOfMonth(1);
    LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
    LocalDate startOfLastMonth = startOfMonth.minusMonths(1);

    long totalClients = clientJpaRepository.count();
    long newClientsThisMonth =
        clientJpaRepository.findAllFiltered(null, true, Pageable.unpaged()).stream()
            .filter(
                c ->
                    c.getCreatedAt() != null
                        && !c.getCreatedAt().toLocalDate().isBefore(startOfMonth))
            .count();

    long appointmentsToday = appointmentJpaRepository.findActiveByDate(today).size();
    long appointmentsThisWeek =
        appointmentJpaRepository.findByDateRange(startOfWeek, endOfWeek).stream()
            .filter(a -> !"CANCELLED".equals(a.getStatus()))
            .count();
    long appointmentsThisMonth =
        appointmentJpaRepository.findByDateRange(startOfMonth, endOfMonth).stream()
            .filter(a -> !"CANCELLED".equals(a.getStatus()))
            .count();

    var monthEntries =
        financialEntryJpaRepository.findWithFilters(
            startOfMonth, endOfMonth, null, null, null, Pageable.unpaged());
    BigDecimal revenueThisMonth =
        monthEntries.stream()
            .filter(e -> "INCOME".equals(e.getType()))
            .map(e -> e.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal expenseThisMonth =
        monthEntries.stream()
            .filter(e -> "EXPENSE".equals(e.getType()))
            .map(e -> e.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    long lowStockItems =
        inventoryItemJpaRepository.findAll().stream()
            .filter(
                i ->
                    i.isActive()
                        && i.getCurrentQuantity().compareTo(BigDecimal.ZERO) > 0
                        && i.getCurrentQuantity().compareTo(i.getMinimumQuantity()) < 0)
            .count();
    long outOfStockItems =
        inventoryItemJpaRepository.findAll().stream()
            .filter(i -> i.isActive() && i.getCurrentQuantity().compareTo(BigDecimal.ZERO) == 0)
            .count();

    List<MonthlyRevenueStat> last6Months = buildLast6Months(today);

    return new DashboardSummary(
        totalClients,
        newClientsThisMonth,
        appointmentsToday,
        appointmentsThisWeek,
        appointmentsThisMonth,
        revenueThisMonth,
        expenseThisMonth,
        lowStockItems,
        outOfStockItems,
        last6Months);
  }

  private List<MonthlyRevenueStat> buildLast6Months(LocalDate today) {
    List<MonthlyRevenueStat> result = new ArrayList<>();
    for (int i = 5; i >= 0; i--) {
      LocalDate ref = today.minusMonths(i);
      LocalDate start = ref.withDayOfMonth(1);
      LocalDate end = ref.withDayOfMonth(ref.lengthOfMonth());

      var entries =
          financialEntryJpaRepository.findWithFilters(
              start, end, null, null, null, Pageable.unpaged());

      BigDecimal revenue =
          entries.stream()
              .filter(e -> "INCOME".equals(e.getType()))
              .map(e -> e.getAmount())
              .reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal expense =
          entries.stream()
              .filter(e -> "EXPENSE".equals(e.getType()))
              .map(e -> e.getAmount())
              .reduce(BigDecimal.ZERO, BigDecimal::add);

      result.add(new MonthlyRevenueStat(ref.getYear(), ref.getMonthValue(), revenue, expense));
    }
    return result;
  }
}
