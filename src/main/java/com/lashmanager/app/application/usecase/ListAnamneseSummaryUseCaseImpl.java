package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.port.in.ListAnamneseSummaryUseCase;
import com.lashmanager.app.domain.port.out.AnamneseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class ListAnamneseSummaryUseCaseImpl implements ListAnamneseSummaryUseCase {
    private final AnamneseRepository anamneseRepository;

    @Override public Page<AnamneseSummaryResult> execute(String search, Pageable pageable) {
        return anamneseRepository.findAllSummary(search, pageable);
    }
}
