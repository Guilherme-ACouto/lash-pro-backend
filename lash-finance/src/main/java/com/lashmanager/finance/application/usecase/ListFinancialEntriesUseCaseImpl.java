package com.lashmanager.finance.application.usecase;

import com.lashmanager.finance.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListFinancialEntriesUseCaseImpl implements ListFinancialEntriesUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public Page<EntryResult> execute(ListQuery query) {
        var pageable = PageRequest.of(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "dueDate"));
        return repository.listWithFilters(
                query.from(), query.to(),
                query.category(), query.expenseType(), query.type(),
                pageable
        ).map(w -> FinancialEntryMapper.toResult(w.entry(), w.counterpart()));
    }

    @Override
    public List<String> findDistinctCategories() {
        return repository.findDistinctCategories();
    }
}
