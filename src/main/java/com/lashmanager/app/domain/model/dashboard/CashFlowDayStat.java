package com.lashmanager.app.domain.model.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CashFlowDayStat(
        LocalDate date,
        BigDecimal income,
        BigDecimal expense
) {}
