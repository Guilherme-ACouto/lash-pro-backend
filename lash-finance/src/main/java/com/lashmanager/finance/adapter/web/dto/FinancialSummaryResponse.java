package com.lashmanager.finance.adapter.web.dto;

import com.lashmanager.finance.domain.model.MonthlyFinancialStat;
import com.lashmanager.finance.domain.port.in.GetFinancialSummaryUseCase;

import java.math.BigDecimal;
import java.util.List;

public record FinancialSummaryResponse(
        BigDecimal predictedMonthResult,
        BigDecimal currentBalance,
        BigDecimal incomeReceived,
        BigDecimal incomePredicted,
        BigDecimal expensePaid,
        BigDecimal expensePredicted,
        List<MonthlyFinancialStat> last6Months
) {
    public static FinancialSummaryResponse from(GetFinancialSummaryUseCase.SummaryResult r) {
        return new FinancialSummaryResponse(
                r.predictedMonthResult(), r.currentBalance(),
                r.incomeReceived(), r.incomePredicted(),
                r.expensePaid(), r.expensePredicted(),
                r.last6Months()
        );
    }
}
