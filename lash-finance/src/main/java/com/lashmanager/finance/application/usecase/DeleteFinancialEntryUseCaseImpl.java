package com.lashmanager.finance.application.usecase;

import com.lashmanager.finance.domain.exception.FinancialEntryLinkedToAppointmentException;
import com.lashmanager.finance.domain.exception.FinancialEntryNotFoundException;
import com.lashmanager.finance.domain.port.in.DeleteFinancialEntryUseCase;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFinancialEntryUseCaseImpl implements DeleteFinancialEntryUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public void execute(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new FinancialEntryNotFoundException(id);
        }
        if (!repository.existsByIdAndAppointmentIdIsNull(id)) {
            throw new FinancialEntryLinkedToAppointmentException();
        }
        repository.delete(id);
    }
}
