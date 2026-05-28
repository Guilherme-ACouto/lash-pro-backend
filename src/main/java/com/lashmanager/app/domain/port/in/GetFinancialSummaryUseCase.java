package com.lashmanager.app.domain.port.in;

import com.lashmanager.app.domain.model.MonthlyFinancialStat;

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
            List<MonthlyFinancialStat> monthlySeries
    ) {}

    SummaryResult execute();
}
