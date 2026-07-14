package com.lashmanager.finance.application.usecase;

import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.model.FinancialEntryExpenseType;
import com.lashmanager.finance.domain.model.FinancialEntryStatus;
import com.lashmanager.finance.domain.model.FinancialEntryType;
import com.lashmanager.finance.domain.port.in.CreateFinancialEntryUseCase;
import com.lashmanager.finance.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateFinancialEntryUseCaseImpl implements CreateFinancialEntryUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public ListFinancialEntriesUseCase.EntryResult execute(CreateCommand command) {
        FinancialEntryStatus status = command.paymentDate() != null
                ? FinancialEntryStatus.PAID : FinancialEntryStatus.PENDING;

        FinancialEntry entry = FinancialEntry.builder()
                .id(UUID.randomUUID())
                .type(FinancialEntryType.valueOf(command.type()))
                .expenseType(command.expenseType() != null
                        ? FinancialEntryExpenseType.valueOf(command.expenseType()) : null)
                .description(command.description())
                .amount(command.amount())
                .dueDate(command.dueDate())
                .paymentDate(command.paymentDate())
                .status(status)
                .category(command.category())
                .paymentMethod(command.paymentMethod())
                .receivedFrom(command.receivedFrom())
                .notes(command.notes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        FinancialEntry saved = repository.save(entry);
        return FinancialEntryMapper.toResult(saved, command.receivedFrom());
    }
}
