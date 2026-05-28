package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.port.in.ListMappingSummaryUseCase;
import com.lashmanager.app.domain.port.out.LashMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class ListMappingSummaryUseCaseImpl implements ListMappingSummaryUseCase {
    private final LashMappingRepository repository;

    @Override public Page<MappingSummaryResult> execute(String search, Pageable pageable) {
        return repository.findAllSummary(search, pageable);
    }
}
