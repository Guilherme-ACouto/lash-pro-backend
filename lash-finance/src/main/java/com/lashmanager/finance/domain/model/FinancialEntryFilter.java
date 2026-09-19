package com.lashmanager.finance.domain.model;

import java.time.LocalDate;

public record FinancialEntryFilter(
        LocalDate from, LocalDate to, String category, String expenseType, String type, int page, int size) {}
