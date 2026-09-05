package com.lashmanager.finance.domain.port.in;

import com.lashmanager.finance.domain.model.FinancialEntryDetails;
import com.lashmanager.finance.domain.model.FinancialEntryFilter;
import com.lashmanager.finance.domain.model.FinancialSummary;

import java.util.List;

import org.springframework.data.domain.Page;

public interface FinancialEntryQueryService {

    FinancialSummary getSummary();

    Page<FinancialEntryDetails> list(FinancialEntryFilter filter);

    List<String> findDistinctCategories();
}
