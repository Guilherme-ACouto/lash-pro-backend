package com.lashmanager.finance.application.usecase;

import com.lashmanager.finance.application.command.CreateFinancialEntryCommand;
import com.lashmanager.finance.application.command.UpdateFinancialEntryCommand;
import com.lashmanager.finance.domain.exception.FinancialEntryLinkedToAppointmentException;
import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.model.FinancialEntryExpenseType;
import com.lashmanager.finance.domain.model.FinancialEntryStatus;
import com.lashmanager.finance.domain.model.FinancialEntryType;
import com.lashmanager.finance.domain.port.in.FinancialEntryUseCase;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialEntryUseCaseImpl implements FinancialEntryUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public FinancialEntry create(CreateFinancialEntryCommand command) {
        FinancialEntryStatus status = command.getPaymentDate() != null ? FinancialEntryStatus.PAID : FinancialEntryStatus.PENDING;

        FinancialEntry entry = FinancialEntry.builder().id(UUID.randomUUID()).type(FinancialEntryType.valueOf(command.getType())).expenseType(
                command.getExpenseType() != null ? FinancialEntryExpenseType.valueOf(command.getExpenseType()): null)
                .description(command.getDescription())
                .amount(command.getAmount())
                .dueDate(command.getDueDate())
                .paymentDate(command.getPaymentDate())
                .status(status)
                .category(command.getCategory())
                .paymentMethod(command.getPaymentMethod())
                .receivedFrom(command.getReceivedFrom())
                .notes(command.getNotes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return repository.save(entry);
    }

    @Override
    public void update(FinancialEntry entry, UpdateFinancialEntryCommand command) {
        if (entry.getAppointmentId() != null) {
            throw new FinancialEntryLinkedToAppointmentException();
        }

        entry.update(command);
        repository.save(entry);
    }

    @Override
    public void delete(FinancialEntry entry) {
        if (entry.getAppointmentId() != null) {
            throw new FinancialEntryLinkedToAppointmentException();
        }
        repository.delete(entry.getId());
    }

    @Override
    public void togglePaid(FinancialEntry entry) {
        entry.togglePaid();
        repository.save(entry);
    }
}
