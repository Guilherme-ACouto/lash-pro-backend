package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.FinancialEntryNotFoundException;
import com.lashmanager.app.domain.model.FinancialEntry;
import com.lashmanager.app.domain.model.FinancialEntryExpenseType;
import com.lashmanager.app.domain.model.FinancialEntryStatus;
import com.lashmanager.app.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.app.domain.port.in.UpdateFinancialEntryUseCase;
import com.lashmanager.app.domain.port.out.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateFinancialEntryUseCaseImpl implements UpdateFinancialEntryUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public ListFinancialEntriesUseCase.EntryResult execute(UpdateCommand command) {
        FinancialEntry existing = repository.findById(command.id())
                .orElseThrow(() -> new FinancialEntryNotFoundException(command.id()));

        FinancialEntry updated;
        if (existing.getAppointmentId() != null) {
            // Entrada vinculada: somente campos permitidos
            FinancialEntryStatus status = command.paymentDate() != null
                    ? FinancialEntryStatus.PAID
                    : FinancialEntryStatus.PENDING;

            updated = FinancialEntry.builder()
                    .id(existing.getId())
                    .type(existing.getType())
                    .expenseType(existing.getExpenseType())
                    .description(existing.getDescription())
                    .amount(existing.getAmount())
                    .dueDate(existing.getDueDate())
                    .paymentDate(command.paymentDate())
                    .status(status)
                    .category(existing.getCategory())
                    .paymentMethod(command.paymentMethod())
                    .receivedFrom(existing.getReceivedFrom())
                    .notes(command.notes())
                    .appointmentId(existing.getAppointmentId())
                    .createdAt(existing.getCreatedAt())
                    .build();
        } else {
            FinancialEntryStatus status = command.paymentDate() != null
                    ? FinancialEntryStatus.PAID
                    : FinancialEntryStatus.PENDING;

            updated = FinancialEntry.builder()
                    .id(existing.getId())
                    .type(existing.getType())
                    .expenseType(command.expenseType() != null
                            ? FinancialEntryExpenseType.valueOf(command.expenseType())
                            : existing.getExpenseType())
                    .description(command.description() != null ? command.description() : existing.getDescription())
                    .amount(command.amount() != null ? command.amount() : existing.getAmount())
                    .dueDate(command.dueDate() != null ? command.dueDate() : existing.getDueDate())
                    .paymentDate(command.paymentDate())
                    .status(status)
                    .category(command.category() != null ? command.category() : existing.getCategory())
                    .paymentMethod(command.paymentMethod())
                    .receivedFrom(command.receivedFrom() != null ? command.receivedFrom() : existing.getReceivedFrom())
                    .notes(command.notes())
                    .appointmentId(existing.getAppointmentId())
                    .createdAt(existing.getCreatedAt())
                    .build();
        }

        FinancialEntry saved = repository.save(updated);
        return CreateFinancialEntryUseCaseImpl.toResult(saved, saved.getReceivedFrom());
    }
}
