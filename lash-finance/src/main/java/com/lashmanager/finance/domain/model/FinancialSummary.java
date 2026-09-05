package com.lashmanager.finance.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record FinancialSummary(
        BigDecimal predictedMonthResult,
        BigDecimal currentBalance,
        BigDecimal incomeReceived,
        BigDecimal incomePredicted,
        BigDecimal expensePaid,
        BigDecimal expensePredicted,
        List<MonthlyFinancialStat> last6Months) {}
