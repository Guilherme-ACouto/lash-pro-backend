package com.lashmanager.finance.domain.model;

import java.math.BigDecimal;

public record MonthlyFinancialStat(int year, int month, BigDecimal income, BigDecimal expense) {}
