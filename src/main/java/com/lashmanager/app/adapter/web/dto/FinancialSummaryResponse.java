package com.lashmanager.app.adapter.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record FinancialSummaryResponse(
        BigDecimal predictedMonthResult,
        BigDecimal currentBalance,
        BigDecimal incomeReceived,
        BigDecimal incomePredicted,
        BigDecimal expensePaid,
        BigDecimal expensePredicted,
        List<MonthlyStatDTO> monthlySeries
) {
    public record MonthlyStatDTO(int year, int month, BigDecimal income, BigDecimal expense) {}
}
