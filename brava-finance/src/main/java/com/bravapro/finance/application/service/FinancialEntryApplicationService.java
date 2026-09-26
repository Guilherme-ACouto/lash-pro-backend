package com.bravapro.finance.application.service;

import com.bravapro.finance.application.command.CreateFinancialEntryCommand;
import com.bravapro.finance.application.command.DeleteFinancialEntryCommand;
import com.bravapro.finance.application.command.ToggleFinancialEntryPaidCommand;
import com.bravapro.finance.application.command.UpdateFinancialEntryCommand;
import com.bravapro.finance.domain.exception.FinancialEntryNotFoundException;
import com.bravapro.finance.domain.model.FinancialEntry;
import com.bravapro.finance.domain.port.in.FinancialEntryUseCase;
import com.bravapro.finance.domain.port.out.FinancialEntryRepository;

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
