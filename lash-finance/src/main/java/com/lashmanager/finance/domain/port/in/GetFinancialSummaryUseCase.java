package com.lashmanager.finance.domain.port.in;

import com.lashmanager.finance.domain.model.MonthlyFinancialStat;

import java.math.BigDecimal;
import java.util.List;

public interface GetFinancialSummaryUseCase {

    record SummaryResult(
            BigDecimal predictedMonthResult,
            BigDecimal currentBalance,
            BigDecimal incomeReceived,
            BigDecimal incomePredicted,
            BigDecimal expensePaid,
            BigDecimal expensePredicted,
            List<MonthlyFinancialStat> last6Months
    ) {}

    SummaryResult execute();
}
