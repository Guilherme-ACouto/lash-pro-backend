package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.app.domain.port.out.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListFinancialEntriesUseCaseImpl implements ListFinancialEntriesUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public Page<EntryResult> execute(ListQuery query) {
        String category = (query.category() != null && !query.category().isBlank()) ? query.category() : null;
        String expenseType = (query.expenseType() != null && !query.expenseType().isBlank()) ? query.expenseType() : null;
        String type = (query.type() != null && !query.type().isBlank()) ? query.type() : null;

        Page<FinancialEntryRepository.FinancialEntryWithCounterpart> page = repository.listWithFilters(
                query.from(), query.to(), category, expenseType, type,
                PageRequest.of(query.page(), query.size()));

        return page.map(wc -> CreateFinancialEntryUseCaseImpl.toResult(wc.entry(), wc.counterpart()));
    }

    @Override
    public List<String> findDistinctCategories() {
        return repository.findDistinctCategories();
    }
}
