package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.FinancialEntryNotFoundException;
import com.lashmanager.app.domain.model.FinancialEntry;
import com.lashmanager.app.domain.model.FinancialEntryStatus;
import com.lashmanager.app.domain.model.FinancialEntryType;
import com.lashmanager.app.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.app.domain.port.in.ToggleFinancialEntryPaidUseCase;
import com.lashmanager.app.domain.port.out.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToggleFinancialEntryPaidUseCaseImpl implements ToggleFinancialEntryPaidUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public ListFinancialEntriesUseCase.EntryResult execute(UUID id) {
        FinancialEntry existing = repository.findById(id)
                .orElseThrow(() -> new FinancialEntryNotFoundException(id));

        if (existing.getStatus() == FinancialEntryStatus.OVERDUE) {
            throw new IllegalStateException("Lançamento vencido não pode ser alternado via toggle");
        }

        if (existing.getType() == FinancialEntryType.INCOME
                && existing.getStatus() == FinancialEntryStatus.PAID) {
            throw new IllegalStateException("Receita já recebida não pode ser revertida para pendente");
        }

        LocalDate newPaymentDate;
        FinancialEntryStatus newStatus;

        if (existing.getStatus() == FinancialEntryStatus.PENDING) {
            newStatus = FinancialEntryStatus.PAID;
            newPaymentDate = LocalDate.now();
        } else {
            newStatus = FinancialEntryStatus.PENDING;
            newPaymentDate = null;
        }

        FinancialEntry updated = FinancialEntry.builder()
                .id(existing.getId())
                .type(existing.getType())
                .expenseType(existing.getExpenseType())
                .description(existing.getDescription())
                .amount(existing.getAmount())
                .dueDate(existing.getDueDate())
                .paymentDate(newPaymentDate)
                .status(newStatus)
                .category(existing.getCategory())
                .paymentMethod(existing.getPaymentMethod())
                .receivedFrom(existing.getReceivedFrom())
                .notes(existing.getNotes())
                .appointmentId(existing.getAppointmentId())
                .createdAt(existing.getCreatedAt())
                .build();

        FinancialEntry saved = repository.save(updated);
        return CreateFinancialEntryUseCaseImpl.toResult(saved, saved.getReceivedFrom());
    }
}
