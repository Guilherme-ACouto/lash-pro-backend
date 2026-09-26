package com.bravapro.dashboard.application.query;

import com.bravapro.appointments.infrastructure.persistence.entity.AppointmentEntity;
import com.bravapro.appointments.infrastructure.persistence.repository.AppointmentJpaRepository;
import com.bravapro.clients.infrastructure.persistence.entity.ClientEntity;
import com.bravapro.clients.infrastructure.persistence.repository.ClientJpaRepository;
import com.bravapro.dashboard.domain.model.AppointmentDayStat;
import com.bravapro.dashboard.domain.model.CashFlowDayStat;
import com.bravapro.dashboard.domain.model.DashboardData;
import com.bravapro.dashboard.domain.model.DashboardPeriod;
import com.bravapro.dashboard.domain.model.TodayAppointmentEntry;
import com.bravapro.dashboard.domain.port.in.DashboardQueryService;
import com.bravapro.finance.infrastructure.persistence.entity.FinancialEntryEntity;
import com.bravapro.finance.infrastructure.persistence.repository.FinancialEntryJpaRepository;
import com.bravapro.services.infrastructure.persistence.entity.ServiceEntity;
import com.bravapro.services.infrastructure.persistence.repository.ServiceJpaRepository;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Backend real do Dashboard — o anterior (`GetDashboardSummaryUseCase`/`GetTodayScheduleUseCase`,
 * endpoints {@code /summary}/{@code /today}) estava completamente desconectado do frontend
 * (que chama {@code GET /api/dashboard?period=} esperando {@code DashboardData}); achado e
 * confirmado ao vivo (tela em branco) durante a consolidação da issue #7.
 *
 * <p><b>Decisões assumidas, não verificadas contra uma implementação de referência</b> (o
 * `STATE.md` descreve uma versão anterior — `DashboardRepositoryImpl`/`DashboardResponse` — que
 * também não existe mais no código; não havia nada funcionando pra consultar):
 * <ul>
 *   <li>{@code clientsGrowth}: variação percentual de clientes novos no período vs. o período
 *       anterior de mesmo tamanho (ex.: semana atual vs. semana passada)</li>
 *   <li>{@code revenue}/{@code cashFlowSeries}: baseados em {@code paymentDate} (dinheiro que
 *       realmente entrou/saiu no período) — diferente do filtro por {@code dueDate} usado nas
 *       listagens do módulo Financeiro</li>
 *   <li>{@code receivable}/{@code payable}: saldo pendente total (todos os lançamentos PENDING/
 *       OVERDUE com vencimento até o fim do período), não é uma série diária</li>
 * </ul>
 * Vale conferir cada número contra o que a usuária espera ver na tela.
 */
@Service
@RequiredArgsConstructor
public class DashboardQueryServiceImpl implements DashboardQueryService {

    private final ClientJpaRepository clientJpaRepository;
    private final AppointmentJpaRepository appointmentJpaRepository;
    private final FinancialEntryJpaRepository financialEntryJpaRepository;
    private final ServiceJpaRepository serviceJpaRepository;

    private record DateRange(LocalDate from, LocalDate to) {}

    @Override
    public DashboardData getDashboard(DashboardPeriod period) {
        LocalDate today = LocalDate.now();
        DateRange range = resolveRange(period, today);
        DateRange previousRange = resolvePreviousRange(range);

        long activeClients = clientJpaRepository.countByActive(true);
        double clientsGrowth = computeClientsGrowth(range, previousRange);

        List<AppointmentEntity> appointments = appointmentJpaRepository.findByDateRange(range.from(), range.to());
        long completed = countByStatus(appointments, "COMPLETED");
        long confirmed = countByStatus(appointments, "CONFIRMED");
        long scheduled = countByStatus(appointments, "SCHEDULED");
        long cancelled = countByStatus(appointments, "CANCELLED");

        List<FinancialEntryEntity> paidIncome =
                financialEntryJpaRepository.findPaidByTypeAndPaymentDateBetween("INCOME", range.from(), range.to());
        List<FinancialEntryEntity> paidExpense =
                financialEntryJpaRepository.findPaidByTypeAndPaymentDateBetween("EXPENSE", range.from(), range.to());

        return new DashboardData(
                activeClients,
                clientsGrowth,
                appointments.size(),
                completed,
                confirmed,
                scheduled,
                cancelled,
                sum(paidIncome),
                financialEntryJpaRepository.sumPendingByTypeUpTo("INCOME", range.to()),
                financialEntryJpaRepository.sumPendingByTypeUpTo("EXPENSE", range.to()),
                buildAppointmentsSeries(appointments, range),
                buildCashFlowSeries(paidIncome, paidExpense, range),
                buildTodaySchedule(),
                appointments.stream()
                        .map(a -> a.getScheduledDate().toString())
                        .distinct()
                        .sorted()
                        .toList());
    }

    private DateRange resolveRange(DashboardPeriod period, LocalDate today) {
        return switch (period) {
            case TODAY -> new DateRange(today, today);
            case WEEK -> new DateRange(today.with(DayOfWeek.MONDAY), today.with(DayOfWeek.SUNDAY));
            case MONTH -> new DateRange(today.withDayOfMonth(1), today.withDayOfMonth(today.lengthOfMonth()));
        };
    }

    private DateRange resolvePreviousRange(DateRange current) {
        long days = ChronoUnit.DAYS.between(current.from(), current.to()) + 1;
        return new DateRange(current.from().minusDays(days), current.from().minusDays(1));
    }

    private double computeClientsGrowth(DateRange current, DateRange previous) {
        long currentCount = countNewClients(current);
        long previousCount = countNewClients(previous);
        if (previousCount == 0) {
            return currentCount > 0 ? 100.0 : 0.0;
        }
        return (currentCount - previousCount) / (double) previousCount * 100.0;
    }

    private long countNewClients(DateRange range) {
        return clientJpaRepository.countByCreatedAtBetween(range.from().atStartOfDay(), range.to().atTime(LocalTime.MAX));
    }

    private long countByStatus(List<AppointmentEntity> appointments, String status) {
        return appointments.stream().filter(a -> status.equals(a.getStatus())).count();
    }

    private List<AppointmentDayStat> buildAppointmentsSeries(List<AppointmentEntity> appointments, DateRange range) {
        Map<LocalDate, List<AppointmentEntity>> byDate =
                appointments.stream().collect(Collectors.groupingBy(AppointmentEntity::getScheduledDate));
        List<AppointmentDayStat> series = new ArrayList<>();
        for (LocalDate d = range.from(); !d.isAfter(range.to()); d = d.plusDays(1)) {
            List<AppointmentEntity> dayList = byDate.getOrDefault(d, List.of());
            series.add(new AppointmentDayStat(
                    d.toString(),
                    countByStatus(dayList, "COMPLETED"),
                    countByStatus(dayList, "CONFIRMED"),
                    countByStatus(dayList, "SCHEDULED"),
                    countByStatus(dayList, "CANCELLED")));
        }
        return series;
    }

    private List<CashFlowDayStat> buildCashFlowSeries(
            List<FinancialEntryEntity> paidIncome, List<FinancialEntryEntity> paidExpense, DateRange range) {
        Map<LocalDate, BigDecimal> incomeByDate = sumByPaymentDate(paidIncome);
        Map<LocalDate, BigDecimal> expenseByDate = sumByPaymentDate(paidExpense);
        List<CashFlowDayStat> series = new ArrayList<>();
        for (LocalDate d = range.from(); !d.isAfter(range.to()); d = d.plusDays(1)) {
            series.add(new CashFlowDayStat(
                    d.toString(), incomeByDate.getOrDefault(d, BigDecimal.ZERO), expenseByDate.getOrDefault(d, BigDecimal.ZERO)));
        }
        return series;
    }

    private Map<LocalDate, BigDecimal> sumByPaymentDate(List<FinancialEntryEntity> entries) {
        return entries.stream()
                .collect(Collectors.groupingBy(
                        FinancialEntryEntity::getPaymentDate,
                        Collectors.reducing(BigDecimal.ZERO, FinancialEntryEntity::getAmount, BigDecimal::add)));
    }

    private BigDecimal sum(List<FinancialEntryEntity> entries) {
        return entries.stream().map(FinancialEntryEntity::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<TodayAppointmentEntry> buildTodaySchedule() {
        List<AppointmentEntity> todays = appointmentJpaRepository.findByDate(LocalDate.now());

        Map<UUID, String> clientNames = todays.stream()
                .filter(a -> a.getClientId() != null)
                .map(AppointmentEntity::getClientId)
                .distinct()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> clientJpaRepository.findById(id).map(ClientEntity::getName).orElse("Cliente removido")));

        Map<UUID, String> serviceNames = todays.stream()
                .map(AppointmentEntity::getServiceId)
                .distinct()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> serviceJpaRepository.findById(id).map(ServiceEntity::getName).orElse("Serviço removido")));

        return todays.stream()
                .map(a -> new TodayAppointmentEntry(
                        a.getId(),
                        a.getClientId() != null ? clientNames.getOrDefault(a.getClientId(), "—") : "—",
                        serviceNames.getOrDefault(a.getServiceId(), "—"),
                        a.getScheduledTime() != null ? a.getScheduledTime().toString() : null,
                        a.getStatus()))
                .collect(Collectors.toList());
    }
}
