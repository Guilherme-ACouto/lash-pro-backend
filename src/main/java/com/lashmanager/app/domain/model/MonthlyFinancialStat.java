package com.lashmanager.app.domain.model;

import java.math.BigDecimal;

public record MonthlyFinancialStat(
        int year,
        int month,
        BigDecimal incomeTotal,
        BigDecimal expenseTotal
) {}
