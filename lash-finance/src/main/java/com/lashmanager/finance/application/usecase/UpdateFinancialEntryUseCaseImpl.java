package com.lashmanager.finance.application.usecase;

import com.lashmanager.finance.domain.exception.FinancialEntryLinkedToAppointmentException;
import com.lashmanager.finance.domain.exception.FinancialEntryNotFoundException;
import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.model.FinancialEntryExpenseType;
import com.lashmanager.finance.domain.model.FinancialEntryStatus;
import com.lashmanager.finance.domain.model.FinancialEntryType;
import com.lashmanager.finance.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.finance.domain.port.in.UpdateFinancialEntryUseCase;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateFinancialEntryUseCaseImpl implements UpdateFinancialEntryUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public ListFinancialEntriesUseCase.EntryResult execute(UUID id, UpdateCommand command) {
        FinancialEntry existing = repository.findById(id)
                .orElseThrow(() -> new FinancialEntryNotFoundException(id));

        if (existing.getAppointmentId() != null) {
            throw new FinancialEntryLinkedToAppointmentException();
        }

        FinancialEntryStatus status = command.paymentDate() != null
                ? FinancialEntryStatus.PAID : FinancialEntryStatus.PENDING;

        FinancialEntry updated = existing.toBuilder()
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
                .updatedAt(LocalDateTime.now())
                .build();

        FinancialEntry saved = repository.save(updated);
        return FinancialEntryMapper.toResult(saved, command.receivedFrom());
    }
}
