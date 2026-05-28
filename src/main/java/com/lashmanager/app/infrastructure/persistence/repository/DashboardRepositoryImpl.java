package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.domain.model.dashboard.AppointmentCounts;
import com.lashmanager.app.domain.model.dashboard.AppointmentDayStat;
import com.lashmanager.app.domain.model.dashboard.CashFlowDayStat;
import com.lashmanager.app.domain.model.dashboard.TodayAppointmentStat;
import com.lashmanager.app.domain.port.out.DashboardRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class DashboardRepositoryImpl implements DashboardRepository {

    private final EntityManager em;

    @Override
    public long countActiveClients() {
        return em.createQuery(
                "SELECT COUNT(c) FROM ClientEntity c WHERE c.active = true", Long.class)
                .getSingleResult();
    }

    @Override
    public long countNewClientsInPeriod(LocalDate start, LocalDate end) {
        return em.createQuery(
                "SELECT COUNT(c) FROM ClientEntity c WHERE c.active = true" +
                " AND c.createdAt >= :start AND c.createdAt < :end", Long.class)
                .setParameter("start", start.atStartOfDay())
                .setParameter("end", end.atStartOfDay())
                .getSingleResult();
    }

    @Override
    public AppointmentCounts countAppointmentsByPeriod(LocalDate start, LocalDate end) {
        List<Object[]> rows = em.createQuery(
                "SELECT a.status, COUNT(a) FROM AppointmentEntity a" +
                " WHERE a.scheduledDate >= :start AND a.scheduledDate <= :end" +
                " GROUP BY a.status", Object[].class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();

        long total = 0, completed = 0, confirmed = 0, scheduled = 0, cancelled = 0;
        for (Object[] row : rows) {
            String status = (String) row[0];
            long count = (Long) row[1];
            total += count;
            switch (status) {
                case "COMPLETED" -> completed = count;
                case "CONFIRMED" -> confirmed = count;
                case "SCHEDULED" -> scheduled = count;
                case "CANCELLED" -> cancelled = count;
            }
        }
        return new AppointmentCounts(total, completed, confirmed, scheduled, cancelled);
    }

    @Override
    public List<AppointmentDayStat> appointmentsSeriesByPeriod(LocalDate start, LocalDate end) {
        List<Object[]> rows = em.createQuery(
                "SELECT a.scheduledDate, a.status, COUNT(a) FROM AppointmentEntity a" +
                " WHERE a.scheduledDate >= :start AND a.scheduledDate <= :end" +
                " GROUP BY a.scheduledDate, a.status ORDER BY a.scheduledDate", Object[].class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();

        Map<LocalDate, long[]> map = new LinkedHashMap<>();
        for (Object[] row : rows) {
            LocalDate date = (LocalDate) row[0];
            String status = (String) row[1];
            long count = (Long) row[2];
            long[] counts = map.computeIfAbsent(date, d -> new long[4]);
            switch (status) {
                case "COMPLETED" -> counts[0] = count;
                case "CONFIRMED" -> counts[1] = count;
                case "SCHEDULED" -> counts[2] = count;
                case "CANCELLED" -> counts[3] = count;
            }
        }

        List<AppointmentDayStat> result = new ArrayList<>();
        for (Map.Entry<LocalDate, long[]> entry : map.entrySet()) {
            long[] c = entry.getValue();
            result.add(new AppointmentDayStat(entry.getKey(), c[0], c[1], c[2], c[3]));
        }
        return result;
    }

    @Override
    public BigDecimal sumRevenue(LocalDate start, LocalDate end) {
        BigDecimal result = em.createQuery(
                "SELECT SUM(f.amount) FROM FinancialEntryEntity f" +
                " WHERE f.type = 'INCOME' AND f.status = 'PAID'" +
                " AND f.paymentDate >= :start AND f.paymentDate <= :end", BigDecimal.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal sumReceivable() {
        BigDecimal result = em.createQuery(
                "SELECT SUM(f.amount) FROM FinancialEntryEntity f" +
                " WHERE f.type = 'INCOME' AND f.status IN ('PENDING', 'OVERDUE')", BigDecimal.class)
                .getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal sumPayable() {
        BigDecimal result = em.createQuery(
                "SELECT SUM(f.amount) FROM FinancialEntryEntity f" +
                " WHERE f.type = 'EXPENSE' AND f.status IN ('PENDING', 'OVERDUE')", BigDecimal.class)
                .getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public List<CashFlowDayStat> cashFlowLast7Days() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(6);
        List<Object[]> rows = em.createQuery(
                "SELECT f.paymentDate, f.type, SUM(f.amount) FROM FinancialEntryEntity f" +
                " WHERE f.status = 'PAID' AND f.paymentDate >= :sevenDaysAgo" +
                " GROUP BY f.paymentDate, f.type ORDER BY f.paymentDate", Object[].class)
                .setParameter("sevenDaysAgo", sevenDaysAgo)
                .getResultList();

        Map<LocalDate, BigDecimal[]> map = new LinkedHashMap<>();
        for (Object[] row : rows) {
            LocalDate date = (LocalDate) row[0];
            String type = (String) row[1];
            BigDecimal amount = row[2] != null ? (BigDecimal) row[2] : BigDecimal.ZERO;
            BigDecimal[] amounts = map.computeIfAbsent(date,
                    d -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            if ("INCOME".equals(type)) amounts[0] = amount;
            else amounts[1] = amount;
        }

        List<CashFlowDayStat> result = new ArrayList<>();
        for (Map.Entry<LocalDate, BigDecimal[]> entry : map.entrySet()) {
            result.add(new CashFlowDayStat(entry.getKey(), entry.getValue()[0], entry.getValue()[1]));
        }
        return result;
    }

    @Override
    public List<TodayAppointmentStat> todayAppointments(LocalDate today) {
        List<Object[]> rows = em.createQuery(
                "SELECT a.id, a.client.name, a.service.name, a.scheduledTime, a.status" +
                " FROM AppointmentEntity a" +
                " WHERE a.scheduledDate = :today ORDER BY a.scheduledTime", Object[].class)
                .setParameter("today", today)
                .getResultList();

        List<TodayAppointmentStat> result = new ArrayList<>();
        for (Object[] row : rows) {
            String clientName = row[1] != null ? (String) row[1] : "Cliente não informado";
            result.add(new TodayAppointmentStat(
                    row[0].toString(),
                    clientName,
                    (String) row[2],
                    row[3].toString(),
                    (String) row[4]
            ));
        }
        return result;
    }

    @Override
    public List<LocalDate> daysWithAppointmentsInMonth(LocalDate monthStart, LocalDate monthEnd) {
        return em.createQuery(
                "SELECT DISTINCT a.scheduledDate FROM AppointmentEntity a" +
                " WHERE a.scheduledDate >= :start AND a.scheduledDate <= :end" +
                " AND a.status <> 'CANCELLED' ORDER BY a.scheduledDate", LocalDate.class)
                .setParameter("start", monthStart)
                .setParameter("end", monthEnd)
                .getResultList();
    }
}
