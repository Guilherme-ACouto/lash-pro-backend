package com.lashmanager.dashboard.domain.model;

import java.math.BigDecimal;

public record CashFlowDayStat(String date, BigDecimal income, BigDecimal expense) {}
