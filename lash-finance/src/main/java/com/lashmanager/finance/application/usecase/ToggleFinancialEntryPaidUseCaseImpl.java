package com.lashmanager.finance.application.usecase;

import com.lashmanager.finance.domain.exception.FinancialEntryNotFoundException;
import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.model.FinancialEntryStatus;
import com.lashmanager.finance.domain.model.FinancialEntryType;
import com.lashmanager.finance.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.finance.domain.port.in.ToggleFinancialEntryPaidUseCase;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import com.lashmanager.core.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class ToggleFinancialEntryPaidUseCaseImpl implements ToggleFinancialEntryPaidUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public ListFinancialEntriesUseCase.EntryResult execute(UUID id) {
        FinancialEntry existing = repository.findById(id)
                .orElseThrow(() -> new FinancialEntryNotFoundException(id));

        if (existing.getStatus() == FinancialEntryStatus.OVERDUE) {
            throw new BusinessException("Lançamento vencido não pode ser alternado via toggle");
        }
        if (existing.getType() == FinancialEntryType.INCOME
                && existing.getStatus() == FinancialEntryStatus.PAID) {
            throw new BusinessException("Receita já recebida não pode ser revertida para pendente");
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

        FinancialEntry saved = repository.save(existing.toBuilder()
                .paymentDate(newPaymentDate)
                .status(newStatus)
                .updatedAt(LocalDateTime.now())
                .build());

        return FinancialEntryMapper.toResult(saved, saved.getReceivedFrom());
    }
}
