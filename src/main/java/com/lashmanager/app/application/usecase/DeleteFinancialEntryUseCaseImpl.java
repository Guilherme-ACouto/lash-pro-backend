package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.FinancialEntryLinkedToAppointmentException;
import com.lashmanager.app.domain.exception.FinancialEntryNotFoundException;
import com.lashmanager.app.domain.port.in.DeleteFinancialEntryUseCase;
import com.lashmanager.app.domain.port.out.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteFinancialEntryUseCaseImpl implements DeleteFinancialEntryUseCase {

    private final FinancialEntryRepository repository;

    @Override
    public void execute(UUID id) {
        repository.findById(id)
                .orElseThrow(() -> new FinancialEntryNotFoundException(id));

        if (!repository.existsByIdAndAppointmentIdIsNull(id)) {
            throw new FinancialEntryLinkedToAppointmentException();
        }

        repository.delete(id);
    }
}
