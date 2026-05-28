package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.domain.model.MonthlyFinancialStat;
import com.lashmanager.app.domain.port.out.FinancialSummaryRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FinancialSummaryRepositoryImpl implements FinancialSummaryRepository {

    private final EntityManager em;

    @Override
    public BigDecimal sumIncomePaidInMonth(LocalDate monthStart, LocalDate monthEnd) {
        Object result = em.createQuery("""
                SELECT COALESCE(SUM(f.amount), 0) FROM FinancialEntryEntity f
                WHERE f.type = 'INCOME' AND f.status = 'PAID'
                AND f.paymentDate >= :start AND f.paymentDate <= :end
                """)
                .setParameter("start", monthStart)
                .setParameter("end", monthEnd)
                .getSingleResult();
        return toBigDecimal(result);
    }

    @Override
    public BigDecimal sumIncomeTotalInMonth(LocalDate monthStart, LocalDate monthEnd) {
        Object result = em.createQuery("""
                SELECT COALESCE(SUM(f.amount), 0) FROM FinancialEntryEntity f
                WHERE f.type = 'INCOME' AND f.status IN ('PAID', 'PENDING')
                AND f.dueDate >= :start AND f.dueDate <= :end
                """)
                .setParameter("start", monthStart)
                .setParameter("end", monthEnd)
                .getSingleResult();
        return toBigDecimal(result);
    }

    @Override
    public BigDecimal sumExpensePaidInMonth(LocalDate monthStart, LocalDate monthEnd) {
        Object result = em.createQuery("""
                SELECT COALESCE(SUM(f.amount), 0) FROM FinancialEntryEntity f
                WHERE f.type = 'EXPENSE' AND f.status = 'PAID'
                AND f.paymentDate >= :start AND f.paymentDate <= :end
                """)
                .setParameter("start", monthStart)
                .setParameter("end", monthEnd)
                .getSingleResult();
        return toBigDecimal(result);
    }

    @Override
    public BigDecimal sumExpenseTotalInMonth(LocalDate monthStart, LocalDate monthEnd) {
        Object result = em.createQuery("""
                SELECT COALESCE(SUM(f.amount), 0) FROM FinancialEntryEntity f
                WHERE f.type = 'EXPENSE' AND f.status IN ('PAID', 'PENDING')
                AND f.dueDate >= :start AND f.dueDate <= :end
                """)
                .setParameter("start", monthStart)
                .setParameter("end", monthEnd)
                .getSingleResult();
        return toBigDecimal(result);
    }

    @Override
    public BigDecimal sumAllTimePaidBalance() {
        Object result = em.createQuery("""
                SELECT COALESCE(SUM(CASE WHEN f.type = 'INCOME' THEN f.amount ELSE -f.amount END), 0)
                FROM FinancialEntryEntity f WHERE f.status = 'PAID'
                """)
                .getSingleResult();
        return toBigDecimal(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MonthlyFinancialStat> last6MonthsStats() {
        LocalDate sixMonthsAgo = LocalDate.now().withDayOfMonth(1).minusMonths(5);

        List<Object[]> rows = em.createQuery("""
                SELECT EXTRACT(YEAR FROM f.paymentDate), EXTRACT(MONTH FROM f.paymentDate),
                       f.type, COALESCE(SUM(f.amount), 0)
                FROM FinancialEntryEntity f
                WHERE f.status = 'PAID' AND f.paymentDate >= :sixMonthsAgo
                GROUP BY EXTRACT(YEAR FROM f.paymentDate), EXTRACT(MONTH FROM f.paymentDate), f.type
                ORDER BY 1, 2
                """)
                .setParameter("sixMonthsAgo", sixMonthsAgo)
                .getResultList();

        // Agrupa por (year, month)
        Map<String, BigDecimal[]> map = new HashMap<>();
        for (Object[] row : rows) {
            int year = ((Number) row[0]).intValue();
            int month = ((Number) row[1]).intValue();
            String type = (String) row[2];
            BigDecimal amount = toBigDecimal(row[3]);
            String key = year + "-" + month;
            map.putIfAbsent(key, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            if ("INCOME".equals(type)) {
                map.get(key)[0] = amount;
            } else {
                map.get(key)[1] = amount;
            }
        }

        // Garante que todos os 6 meses aparecem (mesmo sem lançamentos)
        List<MonthlyFinancialStat> stats = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            LocalDate month = LocalDate.now().withDayOfMonth(1).minusMonths(i);
            String key = month.getYear() + "-" + month.getMonthValue();
            BigDecimal[] values = map.getOrDefault(key, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            stats.add(new MonthlyFinancialStat(month.getYear(), month.getMonthValue(), values[0], values[1]));
        }
        return stats;
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) return BigDecimal.ZERO;
        if (value instanceof BigDecimal bd) return bd;
        if (value instanceof Number n) return BigDecimal.valueOf(n.doubleValue());
        return BigDecimal.ZERO;
    }
}
