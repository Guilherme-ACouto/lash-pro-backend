package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.FinancialEntry;
import com.lashmanager.app.domain.model.FinancialEntryExpenseType;
import com.lashmanager.app.domain.model.FinancialEntryStatus;
import com.lashmanager.app.domain.model.FinancialEntryType;
import com.lashmanager.app.domain.port.in.CreateFinancialEntryUseCase;
import com.lashmanager.app.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.app.domain.port.out.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFinancialEntryUseCaseImpl implements CreateFinancialEntryUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public ListFinancialEntriesUseCase.EntryResult execute(CreateCommand command) {
        FinancialEntryStatus status = command.paymentDate() != null
                ? FinancialEntryStatus.PAID
                : FinancialEntryStatus.PENDING;

        FinancialEntry entry = FinancialEntry.builder()
                .type(FinancialEntryType.valueOf(command.type()))
                .expenseType(command.expenseType() != null
                        ? FinancialEntryExpenseType.valueOf(command.expenseType())
                        : null)
                .description(command.description())
                .amount(command.amount())
                .dueDate(command.dueDate())
                .paymentDate(command.paymentDate())
                .status(status)
                .category(command.category())
                .paymentMethod(command.paymentMethod())
                .receivedFrom(command.receivedFrom())
                .notes(command.notes())
                .build();

        FinancialEntry saved = repository.save(entry);
        return toResult(saved, command.receivedFrom());
    }

    static ListFinancialEntriesUseCase.EntryResult toResult(FinancialEntry e, String counterpart) {
        return new ListFinancialEntriesUseCase.EntryResult(
                e.getId(),
                e.getType().name(),
                e.getExpenseType() != null ? e.getExpenseType().name() : null,
                e.getDescription(),
                e.getAmount(),
                e.getDueDate(),
                e.getPaymentDate(),
                e.getStatus().name(),
                e.getCategory(),
                e.getPaymentMethod(),
                counterpart,
                e.getNotes(),
                e.getAppointmentId() != null,
                e.getAppointmentId()
        );
    }
}
