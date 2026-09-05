package com.lashmanager.finance.application.service;

import com.lashmanager.finance.application.command.CreateFinancialEntryCommand;
import com.lashmanager.finance.application.command.DeleteFinancialEntryCommand;
import com.lashmanager.finance.application.command.ToggleFinancialEntryPaidCommand;
import com.lashmanager.finance.application.command.UpdateFinancialEntryCommand;
import com.lashmanager.finance.domain.exception.FinancialEntryNotFoundException;
import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.port.in.FinancialEntryUseCase;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialEntryApplicationService {

    private final FinancialEntryUseCase financialEntryUseCase;
    private final FinancialEntryRepository financialEntryRepository;

    public FinancialEntry when(CreateFinancialEntryCommand command) {
        return financialEntryUseCase.create(command);
    }

    public void when(UpdateFinancialEntryCommand command) {
        financialEntryUseCase.update(getOne(command.getId()), command);
    }

    public void when(DeleteFinancialEntryCommand command) {
        financialEntryUseCase.delete(getOne(command.getId()));
    }

    public void when(ToggleFinancialEntryPaidCommand command) {
        financialEntryUseCase.togglePaid(getOne(command.getId()));
    }

    private FinancialEntry getOne(UUID id) {
        return financialEntryRepository.findById(id).orElseThrow(() -> new FinancialEntryNotFoundException(id));
    }
}
