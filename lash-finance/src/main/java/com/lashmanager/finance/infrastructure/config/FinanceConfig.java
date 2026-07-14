package com.lashmanager.finance.infrastructure.config;

import com.lashmanager.finance.application.usecase.*;
import com.lashmanager.finance.domain.port.in.*;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import com.lashmanager.finance.domain.port.out.FinancialSummaryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinanceConfig {

    @Bean
    public CreateFinancialEntryUseCase createFinancialEntryUseCase(FinancialEntryRepository repo) {
        return new CreateFinancialEntryUseCaseImpl(repo);
    }

    @Bean
    public UpdateFinancialEntryUseCase updateFinancialEntryUseCase(FinancialEntryRepository repo) {
        return new UpdateFinancialEntryUseCaseImpl(repo);
    }

    @Bean
    public DeleteFinancialEntryUseCase deleteFinancialEntryUseCase(FinancialEntryRepository repo) {
        return new DeleteFinancialEntryUseCaseImpl(repo);
    }

    @Bean
    public ListFinancialEntriesUseCase listFinancialEntriesUseCase(FinancialEntryRepository repo) {
        return new ListFinancialEntriesUseCaseImpl(repo);
    }

    @Bean
    public ToggleFinancialEntryPaidUseCase toggleFinancialEntryPaidUseCase(FinancialEntryRepository repo) {
        return new ToggleFinancialEntryPaidUseCaseImpl(repo);
    }

    @Bean
    public GetFinancialSummaryUseCase getFinancialSummaryUseCase(FinancialSummaryRepository summaryRepo) {
        return new GetFinancialSummaryUseCaseImpl(summaryRepo);
    }
}
