package com.bravapro.finance.domain.port.in;

import com.bravapro.finance.domain.model.FinancialEntryDetails;
import com.bravapro.finance.domain.model.FinancialEntryFilter;
import com.bravapro.finance.domain.model.FinancialSummary;

import java.util.List;

import org.springframework.data.domain.Page;

public interface FinancialEntryQueryService {

    FinancialSummary getSummary();

    Page<FinancialEntryDetails> list(FinancialEntryFilter filter);

    List<String> findDistinctCategories();
}
