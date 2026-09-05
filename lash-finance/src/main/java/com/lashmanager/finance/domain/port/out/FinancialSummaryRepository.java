package com.lashmanager.finance.domain.port.out;

import com.lashmanager.finance.domain.model.MonthlyFinancialStat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FinancialSummaryRepository {
    BigDecimal sumIncomePaidInMonth(LocalDate monthStart, LocalDate monthEnd);

    BigDecimal sumIncomeTotalInMonth(LocalDate monthStart, LocalDate monthEnd);

    BigDecimal sumExpensePaidInMonth(LocalDate monthStart, LocalDate monthEnd);

    BigDecimal sumExpenseTotalInMonth(LocalDate monthStart, LocalDate monthEnd);

    BigDecimal sumAllTimePaidBalance();

    List<MonthlyFinancialStat> last6MonthsStats();
}
